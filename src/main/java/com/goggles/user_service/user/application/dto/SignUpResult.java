package com.goggles.user_service.user.application.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResult {
  private UUID userId;

  public static SignUpResult from(UUID keycloakId) {
    return new SignUpResult(keycloakId);
  }
}
