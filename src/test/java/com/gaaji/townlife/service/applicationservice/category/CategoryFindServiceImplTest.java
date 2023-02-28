package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.config.TestBeanConfig;
import com.gaaji.townlife.service.controller.category.dto.CategoryListDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CategoryUnsubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Import(TestBeanConfig.class)
@Transactional
@DisplayName("카테고리 조회 테스트")
class CategoryFindServiceImplTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryUnsubscriptionRepository categoryUnsubscriptionRepository;
    @Autowired
    private CategorySubscriptionServiceImpl categorySubscriptionService;
    @Autowired
    private CategoryFindServiceImpl categoryFindService;

    private final String tester = "tester";
    private int testerId = 1;
    private static int i = 1;

    void init_category() {
        categoryRepository.save(
                Category.create("테스트_카테고리"+ i++, false, "테스트_카테고리입니다.", TownLifeType.POST));
    }

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
        excludedCategories.forEach(categoryId -> categorySubscriptionService.unsubscribe(userId, categoryId));
    }

    @Test
    @DisplayName("회원의 카테고리 리스트 조회 (구독/비구독 구별)")
    public void test_find_list_by_user() {
        init_category_unsubscription();

        List<CategoryListDto> dto = categoryFindService.findList(tester + testerId);

        Assertions.assertNotNull(dto);
        Assertions.assertFalse(dto.isEmpty());
        Assertions.assertEquals(1, dto.stream().filter(CategoryListDto::getIsSubscribed).count());
        Assertions.assertEquals(dto.size()-1, dto.stream().filter(d -> !d.getIsSubscribed()).count());
    }

}