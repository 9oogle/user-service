package com.goggles.user_service.instructor.application.dto;

import com.goggles.user_service.instructor.domain.entity.Instructor;
import java.util.UUID;

public record InstructorApplyCommand(
    UUID requestId, String field, String bio, String career, String portfolio) {

  public Instructor toInstructor() {
    return Instructor.builder()
        .userId(this.requestId)
        .field(this.field)
        .bio(this.bio)
        .career(this.career)
        .portfolio(this.portfolio)
        .build();
  }
}
