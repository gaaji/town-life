package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.config.TestBeanConfig;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceAlreadyExistException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBeanConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("동네생활 게시글 알림구독 서비스 테스트")
class TownLifeSubscriptionServiceImplTest {
    @Autowired
    private TownServiceClient townServiceClient;
    @Autowired
    private AuthServiceClient authServiceClient;
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

        postTownLifeId = townLifeSaveService.save("author01", "1", dto).getId();
    }

    @Test
    @Order(100)
    @DisplayName("동네생활 게시글 알림 요청")
    void test_about_town_life_subscribe() {
        init_town_life();

        townLifeSubscriptionService.subscribe(userId, postTownLifeId);

        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> townLifeSubscriptionService.subscribe(userId, postTownLifeId));
    }

    @Test
    @Order(200)
    @DisplayName("동네생활 게시글 알림 요청 취소")
    void test_about_town_life_unsubscribe() {
        townLifeSubscriptionService.unsubscribe(userId, postTownLifeId);

        Assertions.assertThrows(ResourceRemoveException.class, () -> townLifeSubscriptionService.unsubscribe(userId, postTownLifeId));
    }
}