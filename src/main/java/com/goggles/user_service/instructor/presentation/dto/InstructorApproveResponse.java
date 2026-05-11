package com.goggles.user_service.instructor.presentation.dto;

import com.goggles.user_service.instructor.application.dto.InstructorApproveResult;

public record InstructorApproveResponse(String instructorId, String status) {
  public static InstructorApproveResponse from(InstructorApproveResult result) {
    return new InstructorApproveResponse(
        result.instructorId(), result.status() != null ? result.status().name() : null);
  }
}
