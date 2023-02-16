package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class AdminCategorySaveServiceImplTest {
    @Autowired
    AdminCategorySaveService adminCategorySaveService;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void save() {
        // given
        String name = "질문";
        boolean defaultCategory = true;
        String description = "어쩌구 저쩌구";

        // when
        AdminCategorySaveResponseDto dto = adminCategorySaveService.save(new AdminCategorySaveRequestDto(name, defaultCategory, description));
        Category category = categoryRepository.findById(dto.getId()).get();

        // then
        Assertions.assertEquals(category.getName(), dto.getName());
        Assertions.assertEquals(category.getDescription(), dto.getDescription());
        Assertions.assertEquals(category.isDefaultCategory(), dto.isDefaultCategory());
    }
}