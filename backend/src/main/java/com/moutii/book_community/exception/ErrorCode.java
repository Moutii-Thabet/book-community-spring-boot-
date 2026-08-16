package com.moutii.book_community.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND","username %s not found" , HttpStatus.NOT_FOUND),
    PASSWORD_CONFIRM_MISMATCH("PASSWORD_CONFIRM_MISMATCH","Password and confirm password must be equal" ,HttpStatus.BAD_REQUEST ),
    USERNAME_ALREADY_EXISTS("USERNAME_ALREADY_EXISTS","Username already exists in the database" ,HttpStatus.BAD_REQUEST )
    ;


    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
