package com.gaaji.townlife.service.domain.reaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.gaaji.townlife.global.exceptions.api.exception.BadRequestException;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;

public enum Emoji {
    THUMBS_UP("thumbs_up"),
    LIKE("like"),
    HAPPY("happy"),
    SURPRISED("surprised"),
    SAD("sad"),
    ANGRY("angry");
    private String value;
    Emoji(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
    @JsonCreator
    public static Emoji from(String value) {
        String v = value.toLowerCase();
        for (Emoji emoji : Emoji.values()) {
            if (emoji.getValue().equals(v)) {
                return emoji;
            }
        }
        throw new BadRequestException("올바른 Emoji로 요청해주세요. (ex. 'thumbs_up'&'like'&'happy'&'surprised'&'sad'&'angry')");
    }
}
