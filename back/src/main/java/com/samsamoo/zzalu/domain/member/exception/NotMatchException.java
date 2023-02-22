package com.samsamoo.zzalu.domain.member.exception;

import com.samsamoo.zzalu.global.advice.BadRequestException;

public class NotMatchException extends BadRequestException {
    public NotMatchException(String message) {
        super(message);
    }

}
