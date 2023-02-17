package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TownLifeModifyRequestDto {
    private String title;
    private String text;
    private String location;
}
