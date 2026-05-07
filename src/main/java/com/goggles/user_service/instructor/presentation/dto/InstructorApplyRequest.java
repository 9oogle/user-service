package com.goggles.user_service.instructor.presentation.dto;

import com.goggles.user_service.instructor.application.dto.InstructorApplyCommand;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record InstructorApplyRequest(
    @NotBlank(message = "instructor.validation.field.required") String field,
    @NotBlank(message = "instructor.validation.bio.required") String bio,
    @NotBlank(message = "instructor.validation.career.required") String career,
    String portfolio) {

  public InstructorApplyCommand toServiceRequest(UUID requestId) {
    return new InstructorApplyCommand(requestId, field, bio, career, portfolio);
  }
}
