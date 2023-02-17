package com.gaaji.townlife.service.event.dto;

import lombok.Getter;

@Getter
public class CategoryUpdatedEventBody {
    private CategoryDto before;
    private CategoryDto after;

    public CategoryUpdatedEventBody(String id, CategoryDto before, CategoryDto after) {
        this.before = before;
        this.after = after;
    }

    @Getter
    public static class CategoryDto {
        private final String name;
        private final String description;
        private final boolean isDefault;

        public CategoryDto(String name, String description, boolean isDefault) {
            this.name = name;
            this.description = description;
            this.isDefault = isDefault;
        }
    }

}
