package com.gaaji.townlife.global.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode implements ErrorCode {
    TOWN_LIFE_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0001", "해당 동네생활을 찾을 수 없습니다."),
    TOWN_LIFE_SAVE_ERROR(HttpStatus.BAD_REQUEST, "TL-0002", "동네생활 게시글 등록에 실패하였습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0010", "해당 카테고리를 찾을 수 없습니다."),
    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}