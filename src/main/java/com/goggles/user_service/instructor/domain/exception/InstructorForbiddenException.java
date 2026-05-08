package com.goggles.user_service.instructor.domain.exception;

import com.goggles.common.exception.ForbiddenException;

public class InstructorForbiddenException extends ForbiddenException {

  public InstructorForbiddenException(String message) {
    super(message);
  }
}
