package com.samsamoo.zzalu.global.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    USERNAME_DUPLICATION(400, "M002", "Username is Duplication"),
    NICKNAME_DUPLICATION(400, "M002", "Nickname is Duplication"),
    LOGIN_INPUT_INVALID(400, "M004", "Login input is invalid"),
    USERNAME_NOT_FOUND(400, "M005", "Username is not found"),
    INVALID_PASSWORD(400, "M006", "Invalid password")

    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
