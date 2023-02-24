package com.gaaji.townlife.service.controller.category.dto.builder;

import com.gaaji.townlife.service.controller.category.dto.CategoryListDto;
import com.gaaji.townlife.service.domain.category.Category;

public class CategoryResponseBuilder {

    public static CategoryListDto categoryListDto(Category entity, boolean isSubscribedByUser) {
        return new CategoryListDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTownLifeType(),
                entity.isDefaultCategory(),
                isSubscribedByUser
        );
    }

}
