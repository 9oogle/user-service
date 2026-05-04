package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.GetUserInfoResult;

public record GetUserInfoResponse(
        String userId,
        String userName,
        String userEmail
) {
    public static GetUserInfoResponse from(GetUserInfoResult result) {
        return new GetUserInfoResponse(
                result.userId(),
                result.userName(),
                result.userEmail()
        );
    }
}
