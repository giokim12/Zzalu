package com.samsamoo.zzalu.domain.member.exception;

import com.samsamoo.zzalu.global.advice.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    private static final String MESSAGE = "회원 정보가 정확하지 않습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
    public InvalidPasswordException(String message) {
        super(message);
    }
}
