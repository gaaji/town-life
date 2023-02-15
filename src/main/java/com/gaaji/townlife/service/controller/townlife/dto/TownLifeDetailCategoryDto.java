package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.category.Category;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TownLifeDetailCategoryDto {

    private String categoryId;
    private String categoryName;

    public static TownLifeDetailCategoryDto of(Category entity) {
        return new TownLifeDetailCategoryDto(entity.getId(), entity.getName());
    }
}
