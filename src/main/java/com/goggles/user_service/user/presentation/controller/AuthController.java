package com.goggles.user_service.user.presentation.controller;

import com.goggles.common.response.ApiResponse;
import com.goggles.user_service.user.application.dto.LoginResult;
import com.goggles.user_service.user.application.service.AuthService;
import com.goggles.user_service.user.presentation.dto.LoginRequest;
import com.goggles.user_service.user.presentation.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResult result = authService.login(request.toServiceRequest());
        return ApiResponse.success(LoginResponse.from(result));
    }
}
