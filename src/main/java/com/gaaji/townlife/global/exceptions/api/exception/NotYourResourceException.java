package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class NotYourResourceException extends AbstractApiException {

    public NotYourResourceException(String message) {
        super(ApiErrorCode.NOT_YOUR_RESOURCE, message);
    }

    public NotYourResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
