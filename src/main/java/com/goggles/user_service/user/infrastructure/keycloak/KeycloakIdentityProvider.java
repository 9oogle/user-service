package com.goggles.user_service.user.infrastructure.keycloak;

import com.goggles.user_service.user.domain.entity.TokenResult;
import com.goggles.user_service.user.domain.exception.DuplicateUserException;
import com.goggles.user_service.user.domain.exception.IdentityProviderException;
import com.goggles.user_service.user.domain.exception.InvalidCredentialsException;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.service.IdentityProvider;
import com.goggles.user_service.user.infrastructure.keycloak.config.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakIdentityProvider implements IdentityProvider {

  private final RealmResource realmResource;
  private final KeycloakProperties properties;
  private final RestTemplate restTemplate;

  @Override
  public TokenResult login(String email, String password) {
    String tokenUrl =
        properties.serverUrl() + "/realms/" + properties.realm() + "/protocol/openid-connect/token";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "password");
    params.add("client_id", properties.clientId());
    params.add("client_secret", properties.clientSecret());
    params.add("username", email);
    params.add("password", password);

    try {
      ResponseEntity<Map<String, Object>> response =
          restTemplate.exchange(
              tokenUrl,
              org.springframework.http.HttpMethod.POST,
              new HttpEntity<>(params, headers()),
              new ParameterizedTypeReference<>() {});
      Map<String, Object> body = response.getBody();
      if (body == null || body.get("access_token") == null || body.get("refresh_token") == null) {
        throw new IdentityProviderException("user.login.token.missing");
      }
      return new TokenResult((String) body.get("access_token"), (String) body.get("refresh_token"));
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
        throw new InvalidCredentialsException();
      }
      throw new IdentityProviderException("로그인 실패: " + e.getMessage());
    }
  }

  private HttpHeaders headers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    return headers;
  }

  @Override
  public UUID createUser(String email, String password, String role) {
    UserRepresentation user = new UserRepresentation();
    user.setEnabled(true);
    user.setEmail(email);
    user.setEmailVerified(true);
    user.setUsername(email);
    user.setRequiredActions(List.of());
    CredentialRepresentation credential = getCredential(password);
    user.setCredentials(List.of(credential));

    try (Response response = realmResource.users().create(user)) {
      int status = response.getStatus();

      if (status == HttpStatus.CONFLICT.value()) {
        throw new DuplicateUserException("이미 가입된 이메일입니다.");
      }
      if (status != HttpStatus.CREATED.value()) {
        log.error("Keycloak 유저 생성 실패 - status: {}", status);
        throw new IdentityProviderException("user.registration.failed");
      }
      if (response.getLocation() == null) {
        log.error("Keycloak 응답 헤더에 Location 누락");
        throw new IdentityProviderException("user.registration.userId.missing");
      }

      String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

      assignRole(userId, role);

      return UUID.fromString(userId);

    } catch (DuplicateUserException | IdentityProviderException e) {
      throw e;
    } catch (Exception e) {
      log.error("keycloak 유저 생성 실패", e);
      throw new IdentityProviderException("user.registration.failed");
    }
  }

  @Override
  public void deleteUser(UUID id) {
    try {
      realmResource.users().get(id.toString()).remove();
    } catch (Exception e) {
      log.error("keycloak 회원 삭제 실패 - ID: {}, 사유: {}", id, e.getMessage(), e);
      throw new IdentityProviderException("user.deletion.failed");
    }
  }

  @Override
  public void changePassword(UUID userId, String newPassword) {
    try {
      CredentialRepresentation credential = getCredential(newPassword);
      realmResource.users().get(userId.toString()).resetPassword(credential);
    } catch (jakarta.ws.rs.NotFoundException e) {
      log.error("keycloak에서 사용자를 찾을 수 없음 - ID: {}, 사유: {}", userId, e.getMessage(), e);
      throw new UserNotFoundException("user.notfound");
    } catch (Exception e) {
      log.error("keycloak에서 비밀번호 변경 실패 - ID: {}, 사유: {}", userId, e.getMessage(), e);
      throw new IdentityProviderException("user.changepassword.failed");
    }
  }

  private CredentialRepresentation getCredential(String password) {
    CredentialRepresentation credential = new CredentialRepresentation();
    credential.setType(CredentialRepresentation.PASSWORD);
    credential.setValue(password);
    credential.setTemporary(false);
    return credential;
  }

  private void assignRole(String userId, String roleName) {
    try {
      RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
      realmResource.users().get(userId).roles().realmLevel().add(List.of(role));
    } catch (Exception e) {
      log.error("Keycloak role 할당 실패 - userId: {}, role: {}", userId, roleName, e);
      throw new IdentityProviderException("user.role.assign.failed");
    }
  }
}
