package com.gaaji.townlife.global.utils.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ApiInternalServerErrorException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAuthorizationException;
import com.gaaji.townlife.service.controller.townlife.dto.TownToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ValidateResourceAccess {

    /** Resource 생성 권한 인증 */
    public static void validateAuthorizationSaving(String request, String actual) {
        if ( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_SAVING_ERROR);
        }
    }

    /** Resource 수정 권한 인증 */
    public static void validateAuthorizationModifying(String request, String actual) {
        if( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_MODIFY_ERROR);
        }
    }

    /** Resource 삭제 권한 인증 */
    public static void validateAuthorizationRemoving(String request, String actual) {
        if( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_REMOVE_ERROR);
        }
    }

    /** 특정 ErrorCode에 대한 권한 인증 */
    public static void validateAuthorization(String request, String actual, ApiErrorCode errorCode) {
        if( !Objects.equals(request, actual) ) {
            throw new ResourceAuthorizationException(errorCode);
        }
    }

    /** TownToken 인증 */
    public static String validateAuthenticatedTownToken(String townToken) {
        try {
            TownToken token = new ObjectMapper().readValue(townToken, TownToken.class);
            if(!token.isAuthenticated()) {
                throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_TOWN_ERROR);
            }
            return token.getTownId();

        } catch (JsonProcessingException e) {
            log.error("JSON Parsing 오류가 발생하였습니다. Request value: {}", townToken);
            throw new ApiInternalServerErrorException(e);
        }
    }
}
