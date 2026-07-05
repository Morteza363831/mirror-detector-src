package org.khu.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    private final Object[] args;

    public CustomException(ErrorCode errorCode, Object... args) {

        super(errorCode.getCode());

        this.errorCode = errorCode;
        this.args = args;
    }
}