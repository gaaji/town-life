package com.gaaji.townlife.global.exceptions.internalServer.exception;

import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;
import com.gaaji.townlife.global.exceptions.internalServer.InternalServerBaseException;

public class NullValueException extends InternalServerBaseException {
    public NullValueException() {
        super(InternalErrorCode.ERROR_NULL_VALUE);
    }

    public NullValueException(InternalErrorCode errorCode) {
        super(errorCode);
    }

    public NullValueException(InternalErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public NullValueException(Throwable t) {
        super(InternalErrorCode.ERROR_NULL_VALUE, t);
    }

}
