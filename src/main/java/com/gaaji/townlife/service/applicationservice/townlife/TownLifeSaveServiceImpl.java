package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.global.exception.api.ResourceSaveException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.*;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import com.gaaji.townlife.service.repository.TownLifeSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeSaveServiceImpl implements TownLifeSaveService {

    private final CategoryRepository categoryRepository;
    private final TownLifeRepository townLifeRepository;
    private final TownLifeCounterRepository townLifeCounterRepository;
    private final TownLifeSubscriptionRepository townLifeSubscriptionRepository;

    @Override
    @Transactional
    public TownLifeDetailDto save(TownLifeType type, TownLifeSaveRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));

        TownLifeDetailDto responseDto = null;
        switch (type) {
            case POST:
                PostTownLife postTownLife = saveTownLife(PostTownLife.class, dto, category);
                responseDto = TownLifeDetailDto.of(postTownLife);
                break;
            case QUESTION:
                QuestionTownLife questionTownLife = saveTownLife(QuestionTownLife.class, dto, category);
                responseDto = TownLifeDetailDto.of(questionTownLife);
                break;
        }
        if(responseDto == null) throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR);

        return responseDto;
    }

    private <T extends TownLife> T saveTownLife(Class<T> townLifeClass, TownLifeSaveRequestDto dto, Category category) {
        try {
            T townLife = townLifeRepository.save(
                    townLifeClass.getConstructor(String.class, String.class, TownLifeContent.class)
                            .newInstance(dto.getAuthorId(), dto.getTownId(), TownLifeContent.of(dto.getTitle(), dto.getText(), dto.getLocation())));
            townLife.associateCategory(category);
            saveSubscription(townLife, dto.getAuthorId());
            saveCounter(townLife);
            return townLife;

        } catch (Exception e) {
            throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR, e);
        }
    }

    private <T extends TownLife> void saveSubscription(T townLife, String authorId) {
        TownLifeSubscription subscription = townLifeSubscriptionRepository.save(TownLifeSubscription.of(authorId));
        subscription.associateTownLife(townLife);
    }

    private <T extends TownLife> void saveCounter(T townLife) {
        TownLifeCounter counter = TownLifeCounter.create();
        townLifeCounterRepository.save(counter);
        townLife.associateCounter(counter);
    }

}
