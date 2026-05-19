package com.goggles.user_service.instructor.domain.exception;

import com.goggles.common.exception.ForbiddenException;

public class InstructorNotApprovedException extends ForbiddenException {
  public InstructorNotApprovedException(String message) {
    super(message);
  }
}
