package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceSaveException;
import com.gaaji.townlife.global.utils.validation.ValidateResourceAccess;
import com.gaaji.townlife.service.adapter.gaaji.AuthServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.TownServiceClient;
import com.gaaji.townlife.service.adapter.gaaji.dto.AuthProfileDto;
import com.gaaji.townlife.service.adapter.gaaji.dto.TownAddressDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.builder.TownLifeResponseBuilder;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.*;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import com.gaaji.townlife.service.repository.TownLifeSubscriptionRepository;
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
    private final TownLifeSubscriptionRepository townLifeSubscriptionRepository;
    private final AuthServiceClient authServiceClient;
    private final TownServiceClient townServiceClient;

    @Override
    @Transactional
    public TownLifeDetailDto save(String authId, String townId, TownLifeSaveRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));

        ValidateResourceAccess.validateAuthorizationSaving(dto.getAuthorId(), authId);
        ValidateResourceAccess.validateAuthorization(dto.getTownId(), townId, ApiErrorCode.TOWN_BAD_REQUEST);

        Class<? extends TownLife> townLifeClazz =
                category.getTownLifeType() == TownLifeType.POST ? PostTownLife.class : QuestionTownLife.class;

        TownAddressDto townAddressDto = townServiceClient.getTownAddress(townId);
        TownLife townLife = saveTownLife(townLifeClazz, dto, townAddressDto, category);

        AuthProfileDto authProfileDto = authServiceClient.getAuthProfile(townLife.getAuthorId());
        TownLifeDetailDto responseDto = TownLifeResponseBuilder.townLifeDetailDto(townLife, authProfileDto);

        if(responseDto == null) {
            throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR);
        }

        return responseDto;
    }

    private <T extends TownLife> T saveTownLife(Class<T> clazz, TownLifeSaveRequestDto dto, TownAddressDto townAddressDto, Category category) {
        try {
            T townLife = townLifeRepository.save(
                    clazz.getConstructor(String.class, String.class, String.class, String.class, String.class, String.class)
                            .newInstance(dto.getAuthorId(), dto.getTownId(), townAddressDto.getAddress(), dto.getTitle(), dto.getText(), dto.getLocation()));

            townLife.associateCategory(category);

            saveSubscription(townLife);
            saveCounter(townLife);

            return townLife;

        } catch (Exception e) {
            throw new ResourceSaveException(ApiErrorCode.TOWN_LIFE_SAVE_ERROR, e);
        }
    }

    private <T extends TownLife> void saveSubscription(T townLife) {
        TownLifeSubscription subscription = townLifeSubscriptionRepository.save(TownLifeSubscription.of(townLife.getAuthorId()));
        townLife.addSubscription(subscription);
    }

    private <T extends TownLife> void saveCounter(T townLife) {
        TownLifeCounter counter = townLifeCounterRepository.save(TownLifeCounter.create());
        counter.view();
        townLife.associateCounter(counter);
    }

}
