package com.goggles.user_service.instructor.application.dto;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import com.goggles.user_service.instructor.domain.entity.InstructorStatus;

public record InstructorListResult(String instructorId, String userId, InstructorStatus status) {
  public static InstructorListResult from(Instructor instructor) {
    return new InstructorListResult(
        instructor.getId().getInstructorId(),
        instructor.getUserId().getUserId(),
        instructor.getStatus());
  }
}
