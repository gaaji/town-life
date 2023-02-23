package com.gaaji.townlife.global.utils.validation;

import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidateRequireValue {

    public static <T> void validateRequireNonNull(T obj) throws NullValueException {
        try {
            Objects.requireNonNull(obj);

            if (obj instanceof String) {
                validateRequireNonBlank((String) obj);
            }
        } catch (NullPointerException e) {
            throw new NullValueException(e);
        }
    }

    public static <T> void validateRequireNonNull(T... objs) throws NullValueException {
        try {
            for (T obj : objs) {
                Objects.requireNonNull(obj);

                if (obj instanceof String) {
                    validateRequireNonBlank((String) obj);
                }
            }
        } catch (NullPointerException e) {
            throw new NullValueException(e);
        }
    }

    private static void validateRequireNonBlank(String obj) throws NullValueException {
        if(obj.isBlank() || obj.isEmpty()) {
            throw new NullValueException();
        }
    }

}
