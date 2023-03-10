package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.config.TestBeanConfig;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import com.gaaji.townlife.service.repository.TownLifeSubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(TestBeanConfig.class)
@DisplayName("동네생활 저장 서비스 테스트")
class TownLifeSaveServiceImplTest {

    @Autowired private TownLifeRepository townLifeRepository;
    @Autowired private TownLifeSubscriptionRepository townLifeSubscriptionRepository;
    @Autowired private TownLifeCounterRepository townLifeCounterRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired
    private AuthServiceClient authServiceClient;
    @Autowired
    private TownServiceClient townServiceClient;
    @Autowired
    private TownLifeSaveServiceImpl townLifeSaveService;

//    @BeforeEach
//    void init() {
//        townLifeSaveService = new TownLifeSaveServiceImpl(
//                categoryRepository,
//                townLifeRepository,
//                townLifeCounterRepository,
//                townLifeSubscriptionRepository,
//                new StubAuthServiceClient(),
//                new StubTownServiceClient()
//        );
//    }

    @Test
    @DisplayName("POST 동네생활 저장")
    @Transactional
    void create_new_post_town_life() {
        Category category = categoryRepository.save(
                Category.create("테스트_카테고리_POST_저장", true, "테스트_카테고리입니다.", TownLifeType.POST));

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                        .categoryId(category.getId()).authorId("1").townId("1").title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

        TownLifeDetailDto saveDto = townLifeSaveService.save("1", "1", dto);

        System.out.println("Save: " + saveDto);

        Assertions.assertNotNull(saveDto);
        Assertions.assertNotNull(saveDto.getCategory());
        Assertions.assertEquals(saveDto.getCreatedAt(), saveDto.getUpdatedAt());
        Assertions.assertEquals(dto.getAuthorId(), saveDto.getAuthorId());
        Assertions.assertEquals("foo", saveDto.getAuthorNickName());
        Assertions.assertEquals("테스트동", saveDto.getTownAddress());
        Assertions.assertEquals(dto.getTitle(), saveDto.getTitle());
        Assertions.assertEquals(dto.getText(), saveDto.getText());
        Assertions.assertEquals(dto.getLocation(), saveDto.getLocation());
    }

    @Test
    @DisplayName("QUESTION 동네생활 저장")
    @Transactional
    void create_new_question_town_life() {
        Category category = categoryRepository.save(
                Category.create("테스트_카테고리_QUES_저장", true, "테스트_카테고리입니다.", TownLifeType.QUESTION));

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId("1").townId("1").title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

        TownLifeDetailDto saveDto = townLifeSaveService.save("1", "1", dto);

        System.out.println("Save: " + saveDto);

        Assertions.assertNotNull(saveDto);
        Assertions.assertNotNull(saveDto.getCategory());
        Assertions.assertEquals(saveDto.getCreatedAt(), saveDto.getUpdatedAt());
        Assertions.assertEquals(dto.getAuthorId(), saveDto.getAuthorId());
        Assertions.assertEquals("foo", saveDto.getAuthorNickName());
        Assertions.assertEquals("테스트동", saveDto.getTownAddress());
        Assertions.assertEquals(dto.getTitle(), saveDto.getTitle());
        Assertions.assertEquals(dto.getText(), saveDto.getText());
        Assertions.assertEquals(dto.getLocation(), saveDto.getLocation());
    }

}