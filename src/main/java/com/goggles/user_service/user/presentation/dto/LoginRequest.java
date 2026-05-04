package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.LoginCommand;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String email,
        @NotBlank String password
) {
    public LoginCommand toServiceRequest() {
        return new LoginCommand(email, password);
    }
}
