package com.gaaji.townlife.global.exception.api;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String message) {
        super(ApiErrorCode.BAD_REQUEST, message);
    }
}
