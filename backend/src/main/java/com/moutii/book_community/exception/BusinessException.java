package com.moutii.book_community.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;
  private final Object[] args;


    public BusinessException(ErrorCode errorCode,Object... args) {
        super(formatMessage(errorCode,args));
        this.errorCode = errorCode;
        this.args = args;
    }

  private static String formatMessage(ErrorCode errorCode, Object... args) {
    if (args != null && args.length > 0) {
      return String.format(errorCode.getDefaultMessage(),args);
    }
    return errorCode.getDefaultMessage();
  }
}
