package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.service.controller.category.dto.CategoryListDto;

import java.util.List;

public interface CategoryFindService {

    List<CategoryListDto> findList(String userId);

}
