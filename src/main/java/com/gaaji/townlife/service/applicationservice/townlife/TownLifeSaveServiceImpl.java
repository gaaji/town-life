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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeSaveServiceImpl implements TownLifeSaveService {

    private final CategoryRepository categoryRepository;
    private final TownLifeRepository townLifeRepository;
    private final TownLifeCounterRepository townLifeCounterRepository;

    @Override
    @Transactional
    public TownLifeDetailDto save(TownLifeSaveRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));

        Class<? extends TownLife> townLifeClazz =
                category.getTownLifeType() == TownLifeType.POST ? PostTownLife.class : QuestionTownLife.class;

        TownLife townLife = saveTownLife(townLifeClazz, dto, category);
        TownLifeDetailDto responseDto = TownLifeDetailDto.of(townLife);
        if(responseDto == null) throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR);

        return responseDto;
    }

    private <T extends TownLife> T saveTownLife(Class<T> clazz, TownLifeSaveRequestDto dto, Category category) {
        try {
            T townLife = townLifeRepository.save(
                    clazz.getConstructor(String.class, String.class, String.class, String.class, String.class)
                            .newInstance(dto.getAuthorId(), dto.getTownId(), dto.getTitle(), dto.getText(), dto.getLocation()));

            townLife.associateCategory(category);
            townLife.addSubscription(TownLifeSubscription.of(dto.getAuthorId()));
            saveCounter(townLife);

            return townLife;

        } catch (Exception e) {
            throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR, e);
        }
    }

    private <T extends TownLife> void saveCounter(T townLife) {
        TownLifeCounter counter = townLifeCounterRepository.save(TownLifeCounter.create());
        townLife.associateCounter(counter);
    }

}
