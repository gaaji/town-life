package com.gaaji.townlife.global.exceptions.api.exception;

import com.gaaji.townlife.global.exceptions.api.AbstractApiException;
import com.gaaji.townlife.global.exceptions.api.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler(AbstractApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(AbstractApiException e, HttpServletRequest request) {
        if (e instanceof ApiInternalServerErrorException) {
            log.error("Occurred Api Internal Server Exception");
            e.printStackTrace();
            if(e.getCause()!=null) {
                e.getCause().printStackTrace();
            }
        }
        return ResponseEntity.status(e.getHttpStatus()).body(ErrorResponse.create(e, request.getRequestURI()));
    }
}
