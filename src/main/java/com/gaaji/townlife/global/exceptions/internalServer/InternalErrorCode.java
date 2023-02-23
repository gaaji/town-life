package com.gaaji.townlife.global.exceptions.internalServer;

public enum InternalErrorCode {

    ERROR_FILE_CONVERT("TLIS-0001", "파일 변환 처리에서 오류 발생하였습니다."),
    ERROR_NULL_VALUE("TLIS-0002", "Null 값을 허용하지 않습니다."),
    ERROR_FILE_UPLOAD("TLIS-0003", "파일 업로드 과정에서 오류 발생하였습니다."),
    ERROR_FILE_UPDATE("TLIS-0004", "파일 수정 과정에서 오류가 발생하였습니다."),
    ERROR_FILE_DELETE("TLIS-0005", "파일 삭제 과정에서 오류가 발생하였습니다."),
    ERROR_FILE_TYPE_IMAGE("TLIS-0006", "업로드 파일의 형식이 이미지 파일이 아닙니다."),


    ;

    private String code;
    private String message;

    InternalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
