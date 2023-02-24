package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TownToken {

    private String townId;
    private Boolean isAuthenticated;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
