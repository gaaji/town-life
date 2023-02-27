package com.gaaji.townlife.service.controller.category.dto;

import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDto {

    private String categoryId;
    private String categoryName;
    private String categoryDescription;
    private TownLifeType type;
    private Boolean isDefault;
    private Boolean isSubscribed;

}
