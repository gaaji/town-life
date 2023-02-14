package com.gaaji.townlife.global.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode implements ErrorCode {
    TOWN_LIFE_NOT_FOUND(HttpStatus.NOT_FOUND, "tl-0000", "");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}