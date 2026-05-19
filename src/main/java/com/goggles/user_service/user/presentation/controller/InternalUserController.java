package com.goggles.user_service.user.presentation.controller;

import com.goggles.common.response.ApiResponse;
import com.goggles.user_service.user.domain.entity.User;
import com.goggles.user_service.user.domain.exception.UserNotFoundException;
import com.goggles.user_service.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/user")
public class InternalUserController {

    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    public ApiResponse<UserInfoResponse> getUserInfo(@PathVariable UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user.notfound"));
        return ApiResponse.success(new UserInfoResponse(
                UUID.fromString(user.getId().getUserId()),
                user.getName().getName(),
                user.getEmail().getEmail()
        ));
    }

    public record UserInfoResponse(UUID userId, String userName, String userEmail) {}
}
