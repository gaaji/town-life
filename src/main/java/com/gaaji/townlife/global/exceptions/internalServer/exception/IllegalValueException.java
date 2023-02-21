package com.gaaji.townlife.global.exceptions.internalServer.exception;

import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;
import com.gaaji.townlife.global.exceptions.internalServer.InternalServerBaseException;

public class IllegalValueException extends InternalServerBaseException {

    public IllegalValueException(InternalErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalValueException(InternalErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
