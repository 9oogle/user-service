package com.goggles.user_service.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SignUpResult {
    private UUID userId;

    public static SignUpResult from(UUID keycloakId) {
        return new SignUpResult(keycloakId);
    }
}
