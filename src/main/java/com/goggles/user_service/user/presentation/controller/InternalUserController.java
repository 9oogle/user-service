package com.goggles.user_service.user.presentation.controller;

import com.goggles.user_service.user.presentation.dto.GetUserInfoResponse;
import com.goggles.user_service.user.application.dto.GetUserInfoResult;
import com.goggles.user_service.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/user")
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserInfoResponse> getUserInfo(@PathVariable UUID userId) {
        GetUserInfoResult result = userService.getUserInfo(userId);
        return ResponseEntity.ok(GetUserInfoResponse.from(result));
    }
}
