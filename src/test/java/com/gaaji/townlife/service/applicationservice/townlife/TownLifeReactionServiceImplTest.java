package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.config.TestBeanConfig;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.ReactionDoResponseDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.reaction.Emoji;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestBeanConfig.class)
@DisplayName("동네생활 리액션 서비스 테스트")
class TownLifeReactionServiceImplTest {
    @Autowired
    private TownServiceClient townServiceClient;
    @Autowired
    private AuthServiceClient authServiceClient;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TownLifeSaveServiceImpl townLifeSaveService;
    @Autowired
    private TownLifeReactionServiceImpl townLifeReactionService;
    @Autowired
    private TownLifeFindServiceImpl townLifeFindService;

    private static String postTownLifeId;
    private static String quesTownLifeId;

    void init_post_town_life() {
        Category category = categoryRepository.save(
                Category.create("테스트_카테고리_POST_리액션", true, "테스트_카테고리입니다.", TownLifeType.POST));

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId("1").townId("1").title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

        postTownLifeId = townLifeSaveService.save("1","1",dto).getId();
    }

    void init_question_town_life() {
        Category category = categoryRepository.save(
                Category.create("테스트_카테고리_QUEST_리액션", true, "테스트_카테고리입니다.", TownLifeType.QUESTION));

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId("1").townId("1").title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

        quesTownLifeId = townLifeSaveService.save("1","1",dto).getId();
    }

    private final String testerId = "tester01";

    @Test
    @Order(100)
    @DisplayName("POST 동네생활 리액션 추가 테스트")
    void test_do_post_reaction() {
        init_post_town_life();

        ReactionDoResponseDto responseDto = townLifeReactionService.doReaction(testerId, postTownLifeId, new ReactionDoRequestDto(Emoji.HAPPY));

        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(postTownLifeId, responseDto.getTownLifeId());
        Assertions.assertEquals(Emoji.HAPPY, responseDto.getEmoji());
        Assertions.assertEquals(testerId, responseDto.getUserId());

        TownLifeDetailDto detailDto = townLifeFindService.findById(postTownLifeId);
        Assertions.assertNotNull(detailDto);
        Assertions.assertNotNull(detailDto.getPostReactionDtos());
        Assertions.assertEquals(1, detailDto.getPostReactionDtos().size());
        Assertions.assertNull(detailDto.getQuestionReactionDtos());
    }

    @Test
    @Order(200)
    @DisplayName("QUESTION 동네생활 리액션 추가 테스트")
    void test_do_question_reaction() {
        init_question_town_life();

        ReactionDoResponseDto responseDto = townLifeReactionService.doReaction(testerId, quesTownLifeId, new ReactionDoRequestDto());

        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(quesTownLifeId, responseDto.getTownLifeId());
        Assertions.assertNull(responseDto.getEmoji());
        Assertions.assertEquals(testerId, responseDto.getUserId());

        TownLifeDetailDto detailDto = townLifeFindService.findById(quesTownLifeId);
        Assertions.assertNotNull(detailDto);
        Assertions.assertNotNull(detailDto.getQuestionReactionDtos());
        Assertions.assertEquals(1, detailDto.getQuestionReactionDtos().size());
        Assertions.assertNull(detailDto.getPostReactionDtos());
    }

    @Test
    @Order(300)
    @DisplayName("POST 동네생활 리액션 취소 테스트")
    void test_cancel_post_reaction() {
        townLifeReactionService.cancelReaction(testerId, postTownLifeId);

        TownLifeDetailDto detailDto = townLifeFindService.findById(postTownLifeId);
        Assertions.assertNotNull(detailDto);
        Assertions.assertNotNull(detailDto.getPostReactionDtos());
        Assertions.assertEquals(0, detailDto.getPostReactionDtos().size());
        Assertions.assertNull(detailDto.getQuestionReactionDtos());
    }


    @Test
    @Order(400)
    @DisplayName("QUESTION 동네생활 리액션 취소 테스트")
    void test_cancel_question_reaction() {
        townLifeReactionService.cancelReaction(testerId, quesTownLifeId);

        TownLifeDetailDto detailDto = townLifeFindService.findById(quesTownLifeId);
        Assertions.assertNotNull(detailDto);
        Assertions.assertNotNull(detailDto.getQuestionReactionDtos());
        Assertions.assertEquals(0, detailDto.getQuestionReactionDtos().size());
        Assertions.assertNull(detailDto.getPostReactionDtos());
    }
}