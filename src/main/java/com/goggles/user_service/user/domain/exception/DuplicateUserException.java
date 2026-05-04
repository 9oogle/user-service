package com.goggles.user_service.user.domain.exception;

import com.goggles.common.exception.ConflictException;

public class DuplicateUserException extends ConflictException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
