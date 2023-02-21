package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class ResourceRemoveException extends AbstractApiException {
    public ResourceRemoveException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceRemoveException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
