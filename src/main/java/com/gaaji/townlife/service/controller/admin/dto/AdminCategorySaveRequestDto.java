package com.gaaji.townlife.service.controller.admin.dto;

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

}
