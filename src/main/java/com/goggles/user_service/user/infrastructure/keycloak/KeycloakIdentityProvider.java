package com.goggles.user_service.user.infrastructure.keycloak;

import com.goggles.common.exception.NotFoundException;
import com.goggles.user_service.user.domain.exception.DuplicateUserException;
import com.goggles.user_service.user.domain.exception.IdentityProviderException;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.service.IdentityProvider;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakIdentityProvider implements IdentityProvider {

    private final RealmResource realmResource;

    @Override
    public UUID createUser(String email, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setEmail(email);
        user.setEmailVerified(true);
        user.setUsername(email);
        user.setRequiredActions(List.of());
        CredentialRepresentation credential = getCredential(password);
        user.setCredentials(List.of(credential));

        try(Response response = realmResource.users().create(user)){
            if(response.getStatus() == HttpStatus.CONFLICT.value()){
                throw new DuplicateUserException("이미 가입된 이메일입니다.");
            }

            if(response.getStatus() != HttpStatus.CREATED.value()){
                log.error("Keycloak 유저 생성 실패 - status: {}", response.getStatus());
                throw new IdentityProviderException("user.registration.failed");
            }

            if (response.getLocation() == null) {
                log.error("Keycloak 응답 헤더에 Location 누락");
                throw new IdentityProviderException("user.registration.userId.missing");
            }
            String userId = response.getLocation().getPath()
                    .replaceAll(".*/([^/]+)$", "$1");
            return UUID.fromString(userId);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        try {
            realmResource.users().get(id.toString()).remove();
        } catch (Exception e) {
            log.error("keycloak 회원 삭제 실패 - ID: {}, 사유: {}", id, e.getMessage(), e);
        }
    }

    @Override
    public void changePassword(UUID userId, String newPassword) {
        try {
            CredentialRepresentation credential = getCredential(newPassword);
            realmResource.users().get(userId.toString()).resetPassword(credential);
        } catch (NotFoundException e) {
            log.error("keycloak에서 사용자를 찾을 수 없음 - ID: {}, 사유: {}", userId, e.getMessage(), e);
            throw new UserNotFoundException("user.notfound");
        } catch (Exception e) {
            log.error("keycloak에서 비밀번호 변경 실패 - ID: {}, 사유: {}", userId, e.getMessage(), e);
            throw new IdentityProviderException("user.changepassword.failed");
        }
    }


    private CredentialRepresentation getCredential(String password){
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }
}
