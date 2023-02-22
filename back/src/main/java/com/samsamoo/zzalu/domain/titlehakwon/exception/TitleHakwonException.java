package com.samsamoo.zzalu.domain.titlehakwon.exception;

import com.samsamoo.zzalu.global.advice.NotFoundException;

public class TitleHakwonException extends NotFoundException {
    private static final String MESSAGE = "[ERROR] 해당 제목학원이 존재하지 않습니다.";

    public TitleHakwonException() {
        super(MESSAGE);
    }
    public TitleHakwonException(String message) {
        super(message);
    }
}
