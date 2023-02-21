package com.gaaji.townlife.global.exceptions.internalServer;

import lombok.Getter;

@Getter
public class InternalServerBaseException extends Exception {

    protected InternalErrorCode errorCode;
    protected String code;
    protected String message;
    protected Throwable cause;

    public InternalServerBaseException(InternalErrorCode errorCode) {
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public InternalServerBaseException(InternalErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.cause = cause;
    }
}
