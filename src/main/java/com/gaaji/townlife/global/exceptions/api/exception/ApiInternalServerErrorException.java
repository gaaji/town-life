package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;

public class ApiInternalServerErrorException extends AbstractApiException {

    public ApiInternalServerErrorException() {
        super(ApiErrorCode.INTERNAL_SERVER_ERROR);
    }
    public ApiInternalServerErrorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ApiInternalServerErrorException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ApiInternalServerErrorException(Throwable cause) {
        super(ApiErrorCode.INTERNAL_SERVER_ERROR, cause);
    }

    public ApiInternalServerErrorException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
