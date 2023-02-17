package com.gaaji.townlife.service.controller.admin.dto;

import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AdminCategorySaveRequestDto {
    private String name;
    private boolean defaultCategory; // lombok 의 getter 문제.
    private String description;
    private TownLifeType townLifeType;
}
