package com.gaaji.townlife.service.controller.admin.dto;

import lombok.Getter;

@Getter
public class AdminCategoryModifyDto {
    private String name;
    private String description;
    private boolean defaultCategory;

    public AdminCategoryModifyDto(String name, String description, boolean defaultCategory) {
        this.name = name;
        this.description = description;
        this.defaultCategory = defaultCategory;
    }
}
