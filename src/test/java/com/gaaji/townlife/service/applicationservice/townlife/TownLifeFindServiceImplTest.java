package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TownLifeFindServiceImplTest {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TownLifeRepository townLifeRepository;
    @Autowired private TownLifeCounterRepository townLifeCounterRepository;
    @Autowired private TownLifeFindEntityServiceImpl townLifeFindEntityService;
    @Autowired private TownLifeFindCountServiceImpl townLifeFindCountService;
    @Autowired private TownLifeSaveServiceImpl townLifeSaveService;
    @Autowired private TownLifeFindServiceImpl townLifeFindService;

    private String townLifeId;
    private Category category;

    void init_category() {
        category = categoryRepository.save(Category.create("테스트_카테고리", true, "테스트_카테고리입니다."));
    }

    void init_post_town_life() {
        init_category();

        TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                .categoryId(category.getId()).authorId("1").townId("1").title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();
        townLifeId = townLifeSaveService.save(TownLifeType.POST, dto).getId();
    }

    private static String townId = "1";

    void init_post_town_lives() {
        init_category();
        int n = 100;

        for(int i=1; i<=n; i++) {
            TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                    .categoryId(category.getId()).authorId(String.valueOf(i)).townId(townId)
                    .title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();

            TownLifeDetailDto save = townLifeSaveService.save(TownLifeType.POST, dto);
            if(i==n) System.out.println(save);
        }
    }

    @Test
    void view_post_town_life() {
        init_post_town_life();

        TownLifeDetailDto dto = townLifeFindService.visit(townLifeId);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(townLifeId, dto.getId());
        Assertions.assertEquals(1, dto.getViewCount());
    }

    @Test
    void one_thousand_visit_post_town_life() {
        init_post_town_life();

        TownLifeDetailDto dto = null;
        for(int i=0; i<1000; i++) {
            dto = townLifeFindService.visit(townLifeId);
        }
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(townLifeId, dto.getId());
        Assertions.assertEquals(1000, dto.getViewCount());
    }

    @Test
    void find_list_by_town_id() {
        String defaultLastTownLifeId = "-1";
        int size = 10;

        init_post_town_lives();

        List<TownLifeListDto> firstView = townLifeFindService.findListByTownId(townId, defaultLastTownLifeId, size);
        Assertions.assertNotNull(firstView);
        Assertions.assertNotEquals(0, firstView.size());
        Assertions.assertEquals(size, firstView.size());

        TownLifeListDto lastTownLifeOfFirstView = firstView.get(firstView.size() - 1);
        List<TownLifeListDto> secondView = townLifeFindService.findListByTownId(townId, lastTownLifeOfFirstView.getId(), size);
        Assertions.assertNotNull(secondView);
        Assertions.assertNotEquals(0, secondView.size());
        Assertions.assertEquals(size, firstView.size());

        TownLifeListDto lastTownLifeOfSecondView = secondView.get(secondView.size() - 1);
        Assertions.assertTrue(lastTownLifeOfFirstView.getCreatedAt().isAfter(lastTownLifeOfSecondView.getCreatedAt()));
    }

}