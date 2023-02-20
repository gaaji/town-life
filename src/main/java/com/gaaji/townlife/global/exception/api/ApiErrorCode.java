package com.gaaji.townlife.global.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode implements ErrorCode {
    TOWN_LIFE_NOT_FOUND(HttpStatus.NOT_FOUND, "tl-0001", "해당 동네생활을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "tl-0002", "Category 리소스를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "tl-0003", "잘못된 요청입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}