package com.gaaji.townlife.global.exception.api;

public class ResourceUnmodifiableException extends AbstractApiException {
    public ResourceUnmodifiableException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceUnmodifiableException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
