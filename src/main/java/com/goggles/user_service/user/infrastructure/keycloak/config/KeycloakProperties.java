package com.goggles.user_service.user.infrastructure.keycloak.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(
    @NotBlank String serverUrl,
    @NotBlank String realm,
    @NotBlank String clientId,
    @NotBlank String clientSecret,
    String adminUsername,
    String adminPassword) {}
