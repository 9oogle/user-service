package com.goggles.user_service.instructor.presentation.dto;

import com.goggles.user_service.instructor.application.dto.InstructorApplyResult;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;

public record InstructorApplyResponse(String instructorId, String userId, InstructorStatus status) {
  public static InstructorApplyResponse from(InstructorApplyResult result) {
    return new InstructorApplyResponse(result.instructorId(), result.userId(), result.status());
  }
}
