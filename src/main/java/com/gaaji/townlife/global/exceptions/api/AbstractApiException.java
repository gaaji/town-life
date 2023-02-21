package com.gaaji.townlife.global.exceptions.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractApiException extends RuntimeException implements ErrorCode{
    protected HttpStatus httpStatus;
    protected String errorCode;
    protected String errorName;
    protected String errorMessage;
    protected Throwable cause;

    public AbstractApiException (ErrorCode errorCode) {
        httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
        errorName = errorCode.getErrorName();
        errorMessage = errorCode.getErrorMessage();
    }

    public AbstractApiException(ErrorCode errorCode, Throwable cause) {
        httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
        errorName = errorCode.getErrorName();
        errorMessage = errorCode.getErrorMessage();
        this.cause = cause;
    }
}
