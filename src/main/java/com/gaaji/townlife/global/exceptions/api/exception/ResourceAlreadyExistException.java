package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class ResourceAlreadyExistException extends AbstractApiException {
    public ResourceAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceAlreadyExistException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
