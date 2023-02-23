package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 저장 테스트")
    void save_category() {
        Category category = Category.create("테스트 카테고리", true, "테스트 카테고리입니다.", TownLifeType.POST);
        Category created = categoryRepository.save(category);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertNotNull(created.getName());
        Assertions.assertNotNull(created.getDescription());
        Assertions.assertEquals(0, created.getUnsubscriptions().size());
        Assertions.assertEquals(0, created.getTownLives().size());
        Assertions.assertEquals(category.getName(), created.getName());
        Assertions.assertTrue(created.isDefaultCategory());
    }
}