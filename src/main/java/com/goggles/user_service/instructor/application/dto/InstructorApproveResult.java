package com.goggles.user_service.instructor.application.dto;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;

public record InstructorApproveResult(String instructorId, InstructorStatus status) {
  public static InstructorApproveResult from(Instructor instructor) {
    return new InstructorApproveResult(
        instructor.getId().getInstructorId(), instructor.getStatus());
  }
}
