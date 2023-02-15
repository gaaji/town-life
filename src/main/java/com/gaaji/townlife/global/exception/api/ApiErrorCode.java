package com.gaaji.townlife.global.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode implements ErrorCode {
    TOWN_LIFE_NOT_FOUND(HttpStatus.NOT_FOUND, "tl-0001", "해당 동네생활을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}