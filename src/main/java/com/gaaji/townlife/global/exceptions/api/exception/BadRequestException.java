package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String message) {
        super(ApiErrorCode.BAD_REQUEST, message);
    }
}
