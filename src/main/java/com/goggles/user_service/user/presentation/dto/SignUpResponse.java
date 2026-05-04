package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.SignUpResult;
import java.util.UUID;

public record SignUpResponse(UUID userId) {
  public static SignUpResponse from(SignUpResult response) {
    return new SignUpResponse(response.getUserId());
  }
}
