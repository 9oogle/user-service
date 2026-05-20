package com.goggles.user_service.user.application.service;

import com.goggles.user_service.common.domain.service.RoleCheck;
import com.goggles.user_service.common.domain.service.RoleCheckFactory;
import com.goggles.user_service.user.application.dto.GetMyInfoResult;
import com.goggles.user_service.user.application.dto.GetUserInfoResult;
import com.goggles.user_service.user.application.dto.SignUpCommand;
import com.goggles.user_service.user.application.dto.SignUpResult;
import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.exception.DuplicateUserException;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.repository.UserRepository;
import com.goggles.user_service.user.domain.service.IdentityProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final IdentityProvider identityProvider;
  private final RoleCheckFactory roleCheckFactory;

  @Transactional
  public SignUpResult create(SignUpCommand request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateUserException("이미 가입된 이메일입니다.");
    }
    if (userRepository.existsByNickName(request.getNickName())) {
      throw new DuplicateUserException("이미 사용중인 닉네임입니다.");
    }

    UUID keycloakId = identityProvider.createUser(request.getName(), request.getEmail(),
            request.getPassword());

    try {
      User user = request.toUser(keycloakId);
      userRepository.save(user);
      return SignUpResult.from(keycloakId);
    } catch (Exception e) {
      log.error("DB 저장 실패로 Keycloak 유저 롤백 - keycloakId: {}", keycloakId);
      identityProvider.deleteUser(keycloakId);
      throw e;
    }
  }

  @Transactional(readOnly = true)
  public GetUserInfoResult getUserInfo(UUID userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("user.notfound"));
    return GetUserInfoResult.from(user);
  }

  @Transactional(readOnly = true)
  public GetMyInfoResult getMyInfo(UUID requesterId, Role requesterRole) {
    User user =
        userRepository
            .findById(requesterId)
            .orElseThrow(() -> new UserNotFoundException("user.notfound"));
    RoleCheck roleCheck = roleCheckFactory.create(requesterId, requesterRole, requesterId);
    user.validateAccess(roleCheck);
    return GetMyInfoResult.from(user);
  }
}
