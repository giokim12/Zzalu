package com.samsamoo.zzalu.domain.titlehakwon.exception;

import com.samsamoo.zzalu.global.advice.NotFoundException;

public class CommentNotFoundException  extends NotFoundException {
    private static final String MESSAGE = "[ERROR] 해당 댓글이 존재하지 않습니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }
    public CommentNotFoundException(String message) {
        super(message);
    }
}
