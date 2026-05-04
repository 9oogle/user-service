package com.goggles.user_service.user.domain.repository;

import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.entity.UserId;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    boolean existsByNickName(String nickName);
}
