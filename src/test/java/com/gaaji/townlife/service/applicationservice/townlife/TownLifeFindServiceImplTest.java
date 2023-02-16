package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
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

    private Category category;

    void init_category() {
        category = categoryRepository.save(Category.create("테스트_카테고리", true, "테스트_카테고리입니다."));
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("동네생활 조회 및 조회수 테스트")
    class Test_For_Find_Specific_One_Town_Life {

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
            townLifeId = townLifeSaveService.save(TownLifeType.POST, dto).getId();
        }

        @Test
        @Order(100)
        @DisplayName("동네생활 1회 조회")
        void view_post_town_life() {
            init_post_town_life_for_visit();

            TownLifeDetailDto dto = townLifeFindService.visit(townLifeId);
            Assertions.assertNotNull(dto);
            Assertions.assertEquals(townLifeId, dto.getId());
            Assertions.assertEquals(1, dto.getViewCount());
        }

        @Test
        @Order(200)
        @DisplayName("동네생활 1000회 조회")
        void one_thousand_visit_post_town_life() {
            init_post_town_life_for_visit();

            TownLifeDetailDto dto = null;
            for(int i=0; i<1000; i++) {
                dto = townLifeFindService.visit(townLifeId);
            }
            Assertions.assertNotNull(dto);
            Assertions.assertEquals(townLifeId, dto.getId());
            Assertions.assertEquals(1000, dto.getViewCount());
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("동네생활 메인 페이지 리스트 조회 테스트")
    class Test_For_Find_Town_Life_List_By_Town_Id {
        private final String townId = "town01";
        void init_post_town_lives_for_find_by_town_id() {
            init_category();
            int n = 100;

            for(int i=1; i<=n; i++) {
                TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                        .categoryId(category.getId()).authorId(String.valueOf(i)).townId(townId)
                        .title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();
                townLifeSaveService.save(TownLifeType.POST, dto);
            }
        }

        private final String defaultLastTownLifeId = "-1";
        private final int size = 10;

        @Test
        @Order(100)
        @DisplayName("메인 페이지 리스트 조회 - 메인, 더보기")
        void find_list_by_town_id() {
            init_post_town_lives_for_find_by_town_id();

            List<TownLifeListDto> firstView = townLifeFindService.findListByTownId(townId, defaultLastTownLifeId, size);
            Assertions.assertNotNull(firstView);
            Assertions.assertNotEquals(0, firstView.size());
            Assertions.assertEquals(size, firstView.size());

            TownLifeListDto lastTownLifeOfFirstView = firstView.get(firstView.size() - 1);
            List<TownLifeListDto> moreView = townLifeFindService.findListByTownId(townId, lastTownLifeOfFirstView.getId(), size);
            Assertions.assertNotNull(moreView);
            Assertions.assertNotEquals(0, moreView.size());
            Assertions.assertEquals(size, firstView.size());

            TownLifeListDto lastTownLifeOfSecondView = moreView.get(moreView.size() - 1);
            Assertions.assertTrue(lastTownLifeOfFirstView.getCreatedAt().isAfter(lastTownLifeOfSecondView.getCreatedAt()));
        }

    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("동네생활 내가 작성한 게시글 리스트 조회 테스트")
    class Test_For_Find_Town_Life_List_By_User_Id {
        private final String townId = "town01";
        private final String tester = "tester";
        private int testerId = 1;
        void init_post_town_lives_for_find_by_user_id() {
            init_category();
            int n = 100;

            for(int i=1; i<=n; i++) {
                String authorId  = tester + testerId;
                TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                        .categoryId(category.getId()).authorId(authorId).townId(townId)
                        .title("테스트 게시글 "+ i).text("테스트 게시글 내용입니다.").location("테스트 장소").build();
                testerId = (testerId+1) % 4;
                townLifeSaveService.save(TownLifeType.POST, dto);
            }
        }

        private final String defaultLastTownLifeId = "-1";
        private final String userId = "tester2";
        private final int size = 10;

        @Test
        @Order(100)
        @DisplayName("내 게시글 리스트 조회 - 메인, 더보기")
        void find_list_by_user_id() {
            init_post_town_lives_for_find_by_user_id();

            List<TownLifeListDto> firstView = townLifeFindService.findListByUserId(userId, defaultLastTownLifeId, size);
            Assertions.assertNotNull(firstView);
            Assertions.assertNotEquals(0, firstView.size());
            Assertions.assertEquals(size, firstView.size());

            TownLifeListDto lastTownLifeOfFirstView = firstView.get(firstView.size() - 1);
            List<TownLifeListDto> moreView = townLifeFindService.findListByUserId(userId, lastTownLifeOfFirstView.getId(), size);
            Assertions.assertNotNull(moreView);
            Assertions.assertNotEquals(0, moreView.size());
            Assertions.assertEquals(size, firstView.size());

            TownLifeListDto lastTownLifeOfSecondView = moreView.get(moreView.size() - 1);
            Assertions.assertTrue(lastTownLifeOfFirstView.getCreatedAt().isAfter(lastTownLifeOfSecondView.getCreatedAt()));
        }

    }

}