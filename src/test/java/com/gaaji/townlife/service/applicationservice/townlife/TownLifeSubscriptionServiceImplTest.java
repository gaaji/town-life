package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.exception.ResourceAlreadyExistException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TownLifeSubscriptionServiceImplTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TownLifeSaveServiceImpl townLifeSaveService;
    @Autowired
    private TownLifeRepository townLifeRepository;
    @Autowired
    private TownLifeSubscriptionServiceImpl townLifeSubscriptionService;

    private static String postTownLifeId;
    private final String userId = "tester01";
    void init_town_life() {
        Category category = categoryRepository.save(
                Category.create("테스트_카테고리_동네생활_구독", true, "테스트_카테고리입니다.", TownLifeType.POST));

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId("author01").townId("1")
                .title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

        postTownLifeId = townLifeSaveService.save(dto).getId();
    }

    @Test
    @Order(100)
    @DisplayName("동네생활 게시글 알림 요청")
    void test_about_town_life_subscribe() {
        init_town_life();

        townLifeSubscriptionService.subscribe(postTownLifeId, userId);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> townLifeSubscriptionService.subscribe(postTownLifeId, userId));
    }

    @Test
    @Order(200)
    @DisplayName("동네생활 게시글 알림 요청 취소")
    void test_about_town_life_unsubscribe() {
        townLifeSubscriptionService.unsubscribe(postTownLifeId, userId);

        Assertions.assertThrows(ResourceRemoveException.class, () -> townLifeSubscriptionService.unsubscribe(postTownLifeId, userId));
    }
}