package com.goggles.user_service.user.application.dto;

import com.goggles.user_service.user.domain.entity.User;

public record GetUserInfoResult(String userId, String userName, String userEmail) {
  public static GetUserInfoResult from(User user) {
    return new GetUserInfoResult(
        user.getId().getUserId(), user.getName().getName(), user.getEmail().getEmail());
  }
}
