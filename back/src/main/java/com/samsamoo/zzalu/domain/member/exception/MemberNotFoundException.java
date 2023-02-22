package com.samsamoo.zzalu.domain.member.exception;

import com.samsamoo.zzalu.global.advice.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = "회원이 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
    public MemberNotFoundException(String message) {
        super(message);
    }
}
