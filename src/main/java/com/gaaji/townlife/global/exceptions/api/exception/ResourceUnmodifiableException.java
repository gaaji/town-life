package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class ResourceUnmodifiableException extends AbstractApiException {
    public ResourceUnmodifiableException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceUnmodifiableException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
