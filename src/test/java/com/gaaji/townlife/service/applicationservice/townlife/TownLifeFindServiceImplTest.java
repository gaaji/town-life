package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.applicationservice.category.CategorySubscriptionServiceImpl;
import com.gaaji.townlife.service.controller.category.dto.CategoryUnsubscribeDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListResponseDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CategoryUnsubscriptionRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class TownLifeFindServiceImplTest {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TownLifeRepository townLifeRepository;
    @Autowired private TownLifeCounterRepository townLifeCounterRepository;
    @Autowired private TownLifeFindEntityServiceImpl townLifeFindEntityService;
    @Autowired private TownLifeFindCountServiceImpl townLifeFindCountService;
    @Autowired private TownLifeSaveServiceImpl townLifeSaveService;
    @Autowired private TownLifeFindServiceImpl townLifeFindService;

    private static Category category;
    private static int i = 1;

    void init_category() {
        category = categoryRepository.save(
                Category.create("테스트_카테고리"+ i++, true, "테스트_카테고리입니다.", TownLifeType.POST));
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
            townLifeId = townLifeSaveService.save(dto).getId();
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
        private final String tester = "tester";
        private int testerId = 1;
        void init_post_town_lives_for_find_by_town_id() {
            init_category();
            int n = 100;

            for(int i=1; i<=n; i++) {
                String authorId = tester + testerId;
                TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                        .categoryId(category.getId()).authorId(authorId).townId(townId)
                        .title("테스트 게시글").text("테스트 게시글 내용입니다.").location("테스트 장소").build();
                testerId = (testerId+1) % 4;
                townLifeSaveService.save(dto);
            }
        }

        @Test
        @Order(100)
        @DisplayName("메인 페이지 리스트 조회 - 페이징 3회")
        void find_list_by_town_id_for_three_time() {
            int page = 0, size = 10;
            String authorId = tester + "1";

            init_post_town_lives_for_find_by_town_id();
            LocalDateTime now = LocalDateTime.now();

            for(; page < 3; page++) {
                TownLifeListResponseDto townLives = townLifeFindService.findListByTownId(authorId, townId, now, page, size);
                Assertions.assertNotNull(townLives);
                Assertions.assertNotNull(townLives.getContent());
                Assertions.assertNotEquals(0, townLives.getContent().size());
                Assertions.assertTrue(now.isAfter(townLives.getContent().get(0).getCreatedAt()));
                Assertions.assertTrue(townLives.getHasNext());
            }
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
                townLifeSaveService.save(dto);
            }
        }

        @Test
        @Order(100)
        @DisplayName("내 게시글 리스트 조회 - 페이징 3회")
        void find_list_by_town_id_for_three_time() {
            final String userId = "tester2";
            int page = 0, size = 10;

            init_post_town_lives_for_find_by_user_id();
            LocalDateTime now = LocalDateTime.now();

            for(; page < 3; page++) {
                TownLifeListResponseDto townLives = townLifeFindService.findListByUserId(userId, now, page, size);
                Assertions.assertNotNull(townLives);
                Assertions.assertNotNull(townLives.getContent());
                Assertions.assertNotEquals(0, townLives.getContent().size());
                Assertions.assertTrue(now.isAfter(townLives.getContent().get(0).getCreatedAt()));
            }
        }
    }

    @Autowired
    private CategoryUnsubscriptionRepository categoryUnsubscriptionRepository;
    @Autowired
    private CategorySubscriptionServiceImpl categorySubscriptionService;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("동네생활 리스트 비구독 카테고리 제외 조회 테스트")
    class Test_about_find_list_excluded_category_unsubscription {

        private final String townId = "town01";
        private final String tester = "tester";
        private int testerId = 1;

        void init_category_unsubscription() {
            // 카테고리 6개 초기화
            for(int i=0; i<6; i++) {
                init_category();
            }

            // 전체 카테고리에서 마지막 인덱스를 제외한 카테고리에 대하여 비구독 요청 카테고리 리스트로 매핑
            List<String> excludedCategories = categoryRepository.findAll().stream().map(Category::getId).collect(Collectors.toList());
            excludedCategories.remove(excludedCategories.size()-1);

            // 비구독 요청 카테고리 리스트의 카테고리들을 비구독 요청
            String userId = tester+testerId;
            excludedCategories.forEach(categoryId -> categorySubscriptionService.unsubscribe(categoryId, new CategoryUnsubscribeDto(userId)));
        }

        void init_post_town_lives() {
            init_category_unsubscription();
            List<Category> categories = categoryRepository.findAll();
            int categoryIndex = 0;

            int n = 100;
            for(int i=1; i<=n; i++) {
                String authorId  = tester + testerId;

                String categoryId = categories.get(categoryIndex).getId();
                categoryIndex = (categoryIndex+1) % categories.size();

                TownLifeSaveRequestDto dto = TownLifeSaveRequestDto.builder()
                        .categoryId(categoryId).authorId(authorId).townId(townId)
                        .title("테스트 게시글 "+ i).text("테스트 게시글 내용입니다.").location("테스트 장소").build();
                testerId = (testerId+1) % 4;

                townLifeSaveService.save(dto);
            }
        }

        @Test
        @Order(100)
        @DisplayName("비구독 카테고리 제외 게시글 리스트 조회")
        void find_list_by_town_id_for_three_time() {
            int page = 0, size = 10;
            String authorId = tester + "1";

            init_post_town_lives();
            LocalDateTime now = LocalDateTime.now();

            TownLifeListResponseDto dto;
            do {
                // 전제: 모든 페이지를 응답받을 때까지 게시글을 가져온다.
                dto = townLifeFindService.findListByTownId(authorId, townId, now, page++, size);

                // 1. Response의 카테고리 리스트 가져온다.
                List<TownLifeListDto> townLives = dto.getContent();
                List<String> categories = townLives.stream()
                        .map(TownLifeListDto::getCategoryId).collect(Collectors.toList());

                // 2. 해당 유저의 비구독 카테고리 리스트를 가져온다.
                List<CategoryUnsubscription> unsubscriptions = categoryUnsubscriptionRepository.findByUserId(authorId);
                List<String> unsubscriptionCategories = unsubscriptions.stream()
                        .map(CategoryUnsubscription::getCategory)
                        .map(Category::getId).collect(Collectors.toList());

                // 3. Response의 카테고리들이 비구독 카테고리 리스트에 미포함되어 있는지를 검증한다.
                categories.forEach(categoryId -> Assertions.assertFalse(unsubscriptionCategories.contains(categoryId)));

            } while (dto.getHasNext());

        }
    }

}