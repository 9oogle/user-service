package com.goggles.user_service.user.presentation.dto;

import com.goggles.user_service.user.application.dto.GetMyInfoResult;
import com.goggles.user_service.user.domain.entity.Gender;
import com.goggles.user_service.user.domain.entity.Interest;
import com.goggles.user_service.user.domain.entity.Job;
import com.goggles.user_service.user.domain.entity.Role;
import java.time.LocalDate;
import java.util.List;

public record GetMyInfoResponse(
    String userId,
    String email,
    String name,
    String nickName,
    Role role,
    Gender gender,
    LocalDate birthDate,
    String phoneNumber,
    boolean marketingConsent,
    boolean emailConsent,
    List<Interest> interests,
    List<Job> jobs,
    List<String> educations,
    List<String> majors) {

  public static GetMyInfoResponse from(GetMyInfoResult result) {
    return new GetMyInfoResponse(
        result.userId(),
        result.email(),
        result.name(),
        result.nickName(),
        result.role(),
        result.gender(),
        result.birthDate(),
        result.phoneNumber(),
        result.marketingConsent(),
        result.emailConsent(),
        result.interests(),
        result.jobs(),
        result.educations(),
        result.majors());
  }
}
