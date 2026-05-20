package com.goggles.user_service.user.domain.service;

import com.goggles.user_service.user.domain.entity.TokenResult;
import java.util.UUID;

public interface IdentityProvider {

  UUID createUser(String name, String email, String password);

  void deleteUser(UUID id);

  void changePassword(UUID userId, String newPassword);

  TokenResult login(String email, String password);
}
