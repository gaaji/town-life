package com.gaaji.townlife.service.controller.admin.dto;

import com.gaaji.townlife.service.domain.category.Category;
import lombok.Getter;

@Getter
public class AdminCategorySaveResponseDto {
    private String id;
    private String name;
    private String description;
    private boolean defaultCategory;

    public AdminCategorySaveResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.defaultCategory = category.isDefaultCategory();
        this.description = category.getDescription();
    }
}
