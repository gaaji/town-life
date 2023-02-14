package com.gaaji.townlife.global.exception.api;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends AbstractApiException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
