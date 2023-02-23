package com.gaaji.townlife.global.exceptions.internalServer.entity;

import com.gaaji.townlife.global.exceptions.internalServer.InternalServerBaseException;
import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;

public class EntityException extends InternalServerBaseException {
    public EntityException(InternalErrorCode errorCode) {
        super(errorCode);
    }

    public EntityException(InternalErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
