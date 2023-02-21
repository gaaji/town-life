package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class ResourceAuthorizationException extends AbstractApiException {
    public ResourceAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceAuthorizationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
