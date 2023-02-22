package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryListDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryModifyDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class AdminCategoryServiceTest {
    @Autowired
    AdminCategorySaveService adminCategorySaveService;
    @Autowired
    AdminCategoryFindService adminCategoryFindService;
    @Autowired
    AdminCategoryModifyService adminCategoryModifyService;
    @Autowired
    AdminCategoryRemoveService adminCategoryRemoveService;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void 카테고리_추가() {
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

    @Test
    void 카테고리_조회() {
        // given
        final int categoryCount = 10;
        List<Category> savedCategories = IntStream.range(0, categoryCount).mapToObj(i -> randomCategory()).collect(Collectors.toList());

        // when
        List<AdminCategoryListDto> foundCategories = adminCategoryFindService.findList();

        // then
        Assertions.assertEquals(categoryCount, foundCategories.size());
        Assertions.assertTrue(foundCategories.stream().anyMatch(dto ->
            savedCategories.stream().anyMatch(saved ->
                dto.getId().equals(saved.getId()) &&
                dto.getName().equals(saved.getName()) &&
                dto.getDescription().equals(saved.getDescription()) &&
                dto.isDefaultCategory() == saved.isDefaultCategory()
            )
        ));
    }

    @Test
    void 카테고리_수정() {
        // given
        final int categoryCount = 10;
        List<Category> savedCategories = IntStream.range(0, categoryCount).mapToObj(i -> randomCategory()).collect(Collectors.toList());
        Random random = new Random();

        savedCategories.forEach(category -> {
            String randomName = UUID.randomUUID().toString();
            String randomDescription = UUID.randomUUID().toString();
            boolean randomBoolean = random.nextBoolean();
            adminCategoryModifyService.modify(category.getId(), new AdminCategoryModifyDto(randomName, randomDescription, randomBoolean));
            Category modified = categoryRepository.findById(category.getId()).get();
            Assertions.assertEquals(randomName, modified.getName());
            Assertions.assertEquals(randomDescription, modified.getDescription());
            Assertions.assertEquals(randomBoolean, modified.isDefaultCategory());
        });
    }

    @Test
    void 카테고리_삭제() {
        // given
        final int categoryCount = 10;
        List<Category> savedCategories = IntStream.range(0, categoryCount).mapToObj(i -> randomCategory()).collect(Collectors.toList());

        // when
        savedCategories.forEach(category -> adminCategoryRemoveService.remove(category.getId()));

        // then
        List<AdminCategoryListDto> list = adminCategoryFindService.findList();
        Assertions.assertTrue(list.isEmpty());
    }

    Category randomCategory() {
        return createCategory(UUID.randomUUID().toString(), true, UUID.randomUUID().toString());
    }

    Category createCategory(String name, boolean defaultCategory, String description) {
        AdminCategorySaveResponseDto dto = adminCategorySaveService.save(new AdminCategorySaveRequestDto(name, defaultCategory, description));
        return categoryRepository.findById(dto.getId()).get();
    }

}