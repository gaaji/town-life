package com.gaaji.townlife.global.utils.validation;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAuthorizationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidateResourceAccess {

    public static void validateAuthorizationModifying(String request, String actual) {
        if( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_MODIFY_ERROR);
        }
    }

    public static void validateAuthorizationRemoving(String request, String actual) {
        if( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_REMOVE_ERROR);
        }
    }
}
