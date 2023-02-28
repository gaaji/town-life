package com.gaaji.townlife.service.adapter.gaaji.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthProfileDto {

    private String authId;
    private String nickname;
    private String pictureUrl;
    private double mannerTemperature;

}
