package com.gaaji.townlife.global.exceptions.internalServer.exception;

import com.gaaji.townlife.global.exceptions.internalServer.InternalServerBaseException;
import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;

public class TownLifeAwsS3Exception extends InternalServerBaseException {
    public TownLifeAwsS3Exception(InternalErrorCode errorCode) {
        super(errorCode);
    }

    public TownLifeAwsS3Exception(InternalErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
