package com.goggles.user_service.user.presentation.controller;

import com.goggles.user_service.user.application.dto.SignUpResult;
import com.goggles.user_service.user.application.service.UserService;
import com.goggles.user_service.user.presentation.dto.SignUpRequest;
import com.goggles.user_service.user.presentation.dto.SignUpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest request) {

    SignUpResult response = userService.create(request.toServiceRequest());
    return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponse.from(response));
  }
}
