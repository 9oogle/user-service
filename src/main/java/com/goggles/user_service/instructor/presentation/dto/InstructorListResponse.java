package com.goggles.user_service.instructor.presentation.dto;

import com.goggles.user_service.instructor.application.dto.InstructorListResult;

public record InstructorListResponse(String instructorId, String userId, String status) {
  public static InstructorListResponse from(InstructorListResult result) {
    return new InstructorListResponse(
        result.instructorId(),
        result.userId(),
        result.status() != null ? result.status().name() : null);
  }
}
