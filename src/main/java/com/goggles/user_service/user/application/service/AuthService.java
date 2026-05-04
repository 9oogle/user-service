package com.goggles.user_service.user.application.service;

import com.goggles.user_service.user.application.dto.LoginCommand;
import com.goggles.user_service.user.application.dto.LoginResult;
import com.goggles.user_service.user.domain.entity.TokenResult;
import com.goggles.user_service.user.domain.service.IdentityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final IdentityProvider identityProvider;

  public LoginResult login(LoginCommand command) {
    TokenResult tokenResult = identityProvider.login(command.email(), command.password());
    return LoginResult.from(tokenResult);
  }
}
