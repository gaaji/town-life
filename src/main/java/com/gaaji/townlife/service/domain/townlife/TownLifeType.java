package com.gaaji.townlife.service.domain.townlife;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TownLifeType {
    @JsonProperty("post")
    POST,
    @JsonProperty("question")
    QUESTION
}
