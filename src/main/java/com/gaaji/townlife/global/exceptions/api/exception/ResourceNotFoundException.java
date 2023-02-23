package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends AbstractApiException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
