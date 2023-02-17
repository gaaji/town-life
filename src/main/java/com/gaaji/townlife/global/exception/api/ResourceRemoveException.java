package com.gaaji.townlife.global.exception.api;

public class ResourceRemoveException extends AbstractApiException {
    public ResourceRemoveException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceRemoveException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
