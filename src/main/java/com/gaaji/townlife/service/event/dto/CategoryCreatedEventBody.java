package com.gaaji.townlife.service.event.dto;

import com.gaaji.townlife.service.domain.category.Category;
import lombok.Getter;

@Getter
public class CategoryCreatedEventBody {
    private final String id;
    private final String name;
    private final String description;
    private final boolean isDefault;

    public CategoryCreatedEventBody(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.isDefault = category.isDefaultCategory();
    }
}
