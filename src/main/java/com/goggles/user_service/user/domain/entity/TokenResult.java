package com.goggles.user_service.user.domain.entity;

public record TokenResult(
        String accessToken,
        String refreshToken
) {
}
