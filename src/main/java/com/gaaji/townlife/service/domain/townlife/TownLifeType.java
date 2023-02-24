package com.gaaji.townlife.service.domain.townlife;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gaaji.townlife.global.exceptions.api.exception.BadRequestException;

public enum TownLifeType {

    POST("post"),
    QUESTION("question");

    private String value;

    TownLifeType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TownLifeType from(String value) {
        String v = value.toLowerCase();
        for (TownLifeType type : TownLifeType.values()) {
            if (type.getValue().equals(v)) {
                return type;
            }
        }
        throw new BadRequestException("올바른 TownLifeType으로 요청해주세요. (ex. 'post' & 'question')");
    }
}
