package com.gaaji.townlife.global.exceptions.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode implements ErrorCode {
    TOWN_LIFE_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0001", "해당 게시글을 찾을 수 없습니다."),
    TOWN_LIFE_SAVE_ERROR(HttpStatus.BAD_REQUEST, "TL-0002", "동네생활 게시글 등록에 실패하였습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0003", "해당 카테고리를 찾을 수 없습니다."),
    AUTHORIZATION_MODIFY_ERROR(HttpStatus.UNAUTHORIZED, "TL-0004", "수정에 대한 권한이 없습니다."),
    AUTHORIZATION_REMOVE_ERROR(HttpStatus.UNAUTHORIZED, "TL-0005", "삭제에 대한 권한이 없습니다."),
    TOWN_LIFE_REMOVE_ERROR(HttpStatus.BAD_REQUEST, "TL-0006", "삭제된 게시글입니다."),
    REACTION_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TL-0007", "게시글의 공감해요 및 궁금해요 요청에 실패하였습니다."),
    CATEGORY_SUBSCRIPTION_UNMODIFIABLE_ERROR(HttpStatus.BAD_REQUEST, "TL-0008", "해당 카테고리는 구독 취소할 수 없습니다."),
    CATEGORY_UNSUBSCRIPTION_ALREADY_EXIST_ERROR(HttpStatus.BAD_REQUEST, "TL-0009", "해당 카테고리는 이미 구독 취소하였습니다."),
    REACTION_BY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0010", "유저님은 해당 게시글에 아직 공감하지 않았습니다."),
    TOWN_LIFE_SUBSCRIPTION_ALREADY_EXIST_ERROR(HttpStatus.BAD_REQUEST, "TL-0011", "해당 게시글을 이미 알림 요청하셨습니다."),
    TOWN_LIFE_SUBSCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "TL-0012", "해당 게시글을 아직 알림 요청하지 않았습니다."),
    IMAGE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TL-0013", "이미지 업로드 과정에서 오류가 발생하였습니다."),
    IMAGE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TL-0014", "이미지 수정/삭제 과정에서 오류가 발생하였습니다."),
    IMAGE_REQUIRE_VALUE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "TL-0015", "이미지 업로드/수정/삭제에 요구되는 데이터가 불충분합니다."),
    IMAGE_CONTENT_TYPE_ERROR(HttpStatus.BAD_REQUEST, "TL-0016", "업로드하실 파일은 이미지 파일만 가능합니다."),
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "TL-0017", "업로드한 이미지가 없습니다."),


    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    public String getErrorName() {
        return this.name();
    }
}