package com.goggles.user_service.user.presentation.controller;

import com.goggles.user_service.user.application.dto.GetMyInfoResult;
import com.goggles.user_service.user.application.dto.SignUpResult;
import com.goggles.user_service.user.application.service.UserService;
import com.goggles.user_service.user.domain.entity.Role;
import com.goggles.user_service.user.presentation.dto.GetMyInfoResponse;
import com.goggles.user_service.user.presentation.dto.SignUpRequest;
import com.goggles.user_service.user.presentation.dto.SignUpResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/me")
  public ResponseEntity<GetMyInfoResponse> getMyInfo(
      @RequestHeader("X-User-Id") String userId, @RequestHeader("X-User-Role") String role) {
    GetMyInfoResult result = userService.getMyInfo(UUID.fromString(userId), Role.valueOf(role));

    return ResponseEntity.ok(GetMyInfoResponse.from(result));
  }
}
