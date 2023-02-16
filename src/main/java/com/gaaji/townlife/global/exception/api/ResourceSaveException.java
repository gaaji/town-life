package com.gaaji.townlife.global.exception.api;

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
