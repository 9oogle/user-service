package com.goggles.user_service.instructor.domain.exception;

import com.goggles.common.exception.ConflictException;

public class DuplicateInstructorException extends ConflictException {

  public DuplicateInstructorException(String message) {
    super(message);
  }
}
