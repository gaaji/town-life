package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceSaveException extends AbstractApiException {
    public ResourceSaveException(ErrorCode errorCode) {
        super(errorCode);
    }
    public ResourceSaveException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
