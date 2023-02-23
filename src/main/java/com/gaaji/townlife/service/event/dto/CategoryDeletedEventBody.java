package com.gaaji.townlife.service.event.dto;

import lombok.Getter;

@Getter
public class CategoryDeletedEventBody {
    private final String id;
    private final String name;
    private final String description;
    private final boolean isDefault;

    public CategoryDeletedEventBody(String id, String name, String description, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDefault = isDefault;
    }
}
