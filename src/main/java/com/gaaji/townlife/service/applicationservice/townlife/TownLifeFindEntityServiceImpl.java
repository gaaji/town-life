package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.CategoryUnsubscriptionRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeFindEntityServiceImpl implements TownLifeFindEntityService {

    private final TownLifeRepository townLifeRepository;
    private final CategoryUnsubscriptionRepository categoryUnsubscriptionRepository;

    @Override
    public TownLife findById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

    @Override
    public Slice<TownLife> findListByTownIdAndIdLessThan(String userId, String townId, String offsetTownLifeId, int page, int size) {

        List<Category> excludedCategories = getExcludedCategoriesByUser(userId);

        PageRequest paging = PageRequest.of(page, size);
        Slice<TownLife> townLives = excludedCategories == null ?
                // 구독 취소한 카테고리가 없는 경우
                townLifeRepository.findByTownIdAndIdLessThanAndDeletedAtIsNull(townId, offsetTownLifeId, paging) :
                // 구독 취소한 카테고리가 있는 경우
                townLifeRepository.findByTownIdAndIdLessThanAndCategoryNotInAndDeletedAtIsNull(townId, offsetTownLifeId, excludedCategories, paging);

        validateExistTownLives(townLives.getContent());

        return townLives;
    }

    @Override
    public Slice<TownLife> findListByUserIdAndIdLessThan(String userId, String offsetTownLifeId, int page, int size) {

        PageRequest paging = PageRequest.of(page, size);
        Slice<TownLife> townLives = townLifeRepository.findByAuthorIdAndIdLessThanAndDeletedAtIsNull(userId, offsetTownLifeId, paging);

        validateExistTownLives(townLives.getContent());

        return townLives;
    }

    private void validateExistTownLives(List<TownLife> townLives) {
        if(townLives == null || townLives.size() == 0) {
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
        }
    }

    private List<Category> getExcludedCategoriesByUser(String userId) {
        List<CategoryUnsubscription> categoryUnsubscriptions = categoryUnsubscriptionRepository.findByUserId(userId);

        if(categoryUnsubscriptions.size() == 0) return null;

        return categoryUnsubscriptions.stream()
                .map(CategoryUnsubscription::getCategory).collect(Collectors.toList());
    }
}
