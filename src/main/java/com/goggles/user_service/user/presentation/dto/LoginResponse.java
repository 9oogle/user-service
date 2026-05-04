package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.LoginResult;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(LoginResult result) {
        return new LoginResponse(
                result.accessToken(),
                result.refreshToken()
        );
    }
}
