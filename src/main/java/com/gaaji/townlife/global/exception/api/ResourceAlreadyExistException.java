package com.gaaji.townlife.global.exception.api;

public class ResourceAlreadyExistException extends AbstractApiException {
    public ResourceAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceAlreadyExistException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
