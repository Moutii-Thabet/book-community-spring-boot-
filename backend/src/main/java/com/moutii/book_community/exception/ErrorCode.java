package com.moutii.book_community.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND","username %s not found" , HttpStatus.NOT_FOUND);


    private final String code;
    private final String defaultMessage;
    private final int status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
