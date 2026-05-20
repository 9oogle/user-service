package com.goggles.user_service.user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.goggles.user_service.user.application.dto.SignUpCommand;
import com.goggles.user_service.user.application.dto.SignUpResult;
import com.goggles.user_service.user.domain.entity.Gender;
import com.goggles.user_service.user.domain.entity.Interest;
import com.goggles.user_service.user.domain.entity.Job;
import com.goggles.user_service.user.domain.repository.UserRepository;
import com.goggles.user_service.user.domain.service.IdentityProvider;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Slf4j
@SpringBootTest
public class UserServiceIntegrationTest {

  @Autowired private UserService userService;

  @Autowired private UserRepository userRepository;

  @MockitoBean private IdentityProvider identityProvider;

  private SignUpCommand buildCommand(String email, String nickName) {
    return SignUpCommand.builder()
        .email(email)
        .password("Test1234!")
        .name("홍길동")
        .nickName(nickName)
        .gender(Gender.MALE)
        .birthDate(LocalDate.of(1995, 1, 1))
        .phoneNumber("01012345678")
        .personalInfoConsent(true)
        .marketingConsent(false)
        .emailConsent(false)
        .interests(List.of(Interest.STUDY))
        .jobs(List.of(Job.SOFTWARE_ENGINEER))
        .educations(List.of("서울대학교"))
        .majors(List.of("컴퓨터공학"))
        .build();
  }

  @AfterEach
  void tearDown() {
    // 테스트 후 DB 데이터 정리
    // Keycloak 유저는 직접 콘솔에서 확인 후 수동 삭제
  }

  @Test
  @DisplayName("회원가입 정상 플로우 - Keycloak 생성 및 DB 저장 성공")
  void signUp_success() {
    UUID fakeKeycloakId = UUID.randomUUID();
    given(identityProvider.createUser(anyString(), anyString(), anyString())).willReturn(fakeKeycloakId);

    SignUpCommand command = buildCommand("test@test.com", "테스터");

    SignUpResult result = userService.create(command);

    log.info("userId: {}", result.getUserId());

    assertThat(result.getUserId()).isNotNull();
    assertThat(userRepository.existsByEmail("test@test.com")).isTrue();
  }
}
