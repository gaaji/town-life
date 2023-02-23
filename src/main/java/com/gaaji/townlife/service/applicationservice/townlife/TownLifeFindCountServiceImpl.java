package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeFindCountServiceImpl implements TownLifeFindCountService {

    private final TownLifeCounterRepository townLifeCounterRepository;

    @Override
    @Transactional
    public TownLifeCounter increaseViewCount(String townLifeCounterId) {
        TownLifeCounter townLifeCounter = townLifeCounterRepository.findById(townLifeCounterId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        return townLifeCounter.view();
    }
}
