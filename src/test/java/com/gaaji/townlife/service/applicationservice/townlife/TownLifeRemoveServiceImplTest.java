package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.exception.ResourceAuthorizationException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("동네생활 삭제 서비스 테스트")
class TownLifeRemoveServiceImplTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TownLifeRepository townLifeRepository;
    @Autowired
    private TownLifeCounterRepository townLifeCounterRepository;
    @Autowired
    private TownLifeRemoveServiceImpl townLifeRemoveService;
    @Autowired
    private TownLifeSaveServiceImpl townLifeSaveService;

    private static Category category;

    void init_category() {
        category = categoryRepository.save(
                Category.create("테스트_카테고리_POST_삭제", true, "테스트_카테고리입니다.", TownLifeType.POST));
    }

    private final String authorId = "user01";
    private final String townId = "town01";
    private final String title = "테스트 게시글";
    private final String text = "테스트 게시글 내용입니다.";
    private final String location = "서울특별시 테스트구 테스트동";
    private String townLifeId;

    void init_post_town_life_for_visit() {
        init_category();

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId(authorId).townId(townId)
                .title(title).text(text).location(location).build();
        townLifeId = townLifeSaveService.save(authorId, townId, dto).getId();
    }
    @Test
    @DisplayName("동네생활 게시글 삭제")
    void remove_town_life() {
        init_post_town_life_for_visit();

        Assertions.assertAll(
                () -> Assertions.assertThrows(ResourceAuthorizationException.class, () -> townLifeRemoveService.remove(townLifeId, "mock_author")),
                () -> townLifeRemoveService.remove(townLifeId, authorId),
                () -> Assertions.assertThrows(ResourceNotFoundException.class, () -> townLifeRemoveService.remove(townLifeId, authorId))
        );
    }

}