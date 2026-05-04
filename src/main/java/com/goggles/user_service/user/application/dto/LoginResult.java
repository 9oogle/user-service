package com.goggles.user_service.user.application.dto;

import com.goggles.user_service.user.domain.entity.TokenResult;

public record LoginResult(
        String accessToken,
        String refreshToken
) {
    public static LoginResult from(TokenResult tokenResult) {
        return new LoginResult(
                tokenResult.accessToken(),
                tokenResult.refreshToken()
        );
    }
}
