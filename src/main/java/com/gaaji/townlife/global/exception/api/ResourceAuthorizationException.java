package com.gaaji.townlife.global.exception.api;

public class ResourceAuthorizationException extends AbstractApiException {
    public ResourceAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceAuthorizationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
