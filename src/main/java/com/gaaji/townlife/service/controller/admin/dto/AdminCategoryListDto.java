package com.gaaji.townlife.service.controller.admin.dto;

import lombok.Getter;

@Getter
public class AdminCategoryListDto {
    private String id;
    private String name;
    private String description;
    private boolean defaultCategory;

    public AdminCategoryListDto(String id, String name, String description, boolean defaultCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultCategory = defaultCategory;
    }
}
