package com.goggles.user_service.user.application.dto;

import com.goggles.user_service.user.domain.entity.*;
import java.time.LocalDate;
import java.util.List;

public record GetMyInfoResult(
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
  public static GetMyInfoResult from(User user) {
    return new GetMyInfoResult(
        user.getId().getUserId(),
        user.getEmail().getEmail(),
        user.getName().getName(),
        user.getNickName().getNickName(),
        user.getRole(),
        user.getGender(),
        user.getBirthDate(),
        user.getPhoneNumber().getPhoneNumber(),
        user.getConsent().isMarketing(),
        user.getConsent().isEmail(),
        user.getProfile().getInterests(),
        user.getProfile().getJobs(),
        user.getProfile().getEducations(),
        user.getProfile().getMajors());
  }
}
