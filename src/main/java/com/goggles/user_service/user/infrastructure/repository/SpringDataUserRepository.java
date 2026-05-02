package com.goggles.user_service.user.infrastructure.repository;

import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
}
