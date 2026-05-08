package com.goggles.user_service.instructor.presentation.dto;

import com.goggles.user_service.instructor.application.dto.InstructorApplyResult;

public record InstructorApplyResponse(String instructorId, String userId, String status) {
  public static InstructorApplyResponse from(InstructorApplyResult result) {
    return new InstructorApplyResponse(
            result.instructorId(),
            result.userId(),
            result.status() != null ? result.status().name() : null);
  }
}
