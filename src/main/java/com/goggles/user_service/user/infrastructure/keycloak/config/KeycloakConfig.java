package com.goggles.user_service.user.infrastructure.keycloak.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KeycloakProperties.class)
public class KeycloakConfig {

  private final KeycloakProperties properties;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(properties.serverUrl())
        .realm(properties.realm())
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(properties.clientId())
        .clientSecret(properties.clientSecret())
        .build();
  }

  @Bean
  public RealmResource realmResource(Keycloak keycloak) {
    return keycloak.realm(properties.realm());
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.connectTimeout(Duration.ofSeconds(3)).readTimeout(Duration.ofSeconds(5)).build();
  }
}
