package com.goggles.user_service.domain.exception;

import com.goggles.common.exception.BadRequestException;

public class InvaliNickNameException extends BadRequestException {
    public InvaliNickNameException(String nickName) {
        super("유효하지 않은 닉네임입니다. nickName =" + nickName);
    }
}
