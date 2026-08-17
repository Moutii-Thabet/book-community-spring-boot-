package com.moutii.book_community.handler;

import com.moutii.book_community.exception.BusinessException;
import com.moutii.book_community.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getErrorCode().getDefaultMessage())
                .build();

        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(res);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final UsernameNotFoundException ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ErrorCode.USERNAME_NOT_FOUND.getCode())
                .message(ErrorCode.USERNAME_NOT_FOUND.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.USERNAME_NOT_FOUND.getStatus()).body(res);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(final BadCredentialsException ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ErrorCode.BAD_CREDENTIALS.getCode())
                .message(ErrorCode.BAD_CREDENTIALS.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.BAD_CREDENTIALS.getStatus()).body(res);

    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleException(final DisabledException ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ErrorCode.USER_DISABLED.getCode())
                .message(ErrorCode.USER_DISABLED.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.USER_DISABLED.getStatus()).body(res);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(final EntityNotFoundException ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ErrorCode.USER_NOT_FOUND.getCode())
                .message(ErrorCode.USER_NOT_FOUND.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus()).body(res);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {

        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    errors.add(
                            ErrorResponse.ValidationError.builder()
                                    .code(error.getCode())
                                    .field(((FieldError)error).getField())
                                    .message(error.getDefaultMessage())
                                    .build()
                    );
                });

        final ErrorResponse res = ErrorResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {

        final ErrorResponse res = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(res);

    }



}
