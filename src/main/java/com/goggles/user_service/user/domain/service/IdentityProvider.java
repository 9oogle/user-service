package com.goggles.user_service.user.domain.service;

import java.util.UUID;

public interface IdentityProvider {

    UUID createUser(String email, String password);

    void deleteUser(UUID id);

    void changePassword(UUID userId, String newPassword);
}
