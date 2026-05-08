package com.goggles.user_service.instructor.application.dto;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;

public record InstructorApplyResult(String instructorId, String userId, InstructorStatus status) {
  public static InstructorApplyResult from(Instructor instructor) {
    return new InstructorApplyResult(
        instructor.getId().getInstructorId(),
        instructor.getUserId().getUserId(),
        instructor.getStatus());
  }
}
