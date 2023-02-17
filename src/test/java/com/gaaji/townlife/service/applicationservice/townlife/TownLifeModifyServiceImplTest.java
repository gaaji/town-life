package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeModifyRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TownLifeModifyServiceImplTest {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TownLifeRepository townLifeRepository;
    @Autowired private TownLifeSaveServiceImpl townLifeSaveService;
    @Autowired private TownLifeModifyServiceImpl townLifeModifyService;

    private Category category;
    void init_category() {
        category = categoryRepository.save(
                Category.create("테스트_카테고리", true, "테스트_카테고리입니다.", TownLifeType.POST));
    }

    private final String authorId = "user01";
    private final String townId = "town01";
    private final String title = "테스트 게시글";
    private final String text = "테스트 게시글 내용입니다.";
    private final String location = "서울특별시 테스트구 테스트동";
    private String townLifeId;

    void init_post_town_life() {
        init_category();

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId(authorId).townId(townId)
                .title(title).text(text).location(location).build();
        townLifeId = townLifeSaveService.save(dto).getId();
    }

    @Test
    void test_for_modify_post_town_life() {
        init_post_town_life();
        TownLifeModifyRequestDto dto = new TownLifeModifyRequestDto("수정 게시글", "수정 게시글 내용입니다.", "");

        TownLifeDetailDto modify = townLifeModifyService.modify(townLifeId, authorId, dto);
        System.out.println(modify);
    }

}