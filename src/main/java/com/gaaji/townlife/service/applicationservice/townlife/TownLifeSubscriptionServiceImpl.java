package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeSubscriptionServiceImpl implements TownLifeSubscriptionService {

    private final TownLifeRepository townLifeRepository;

    @Override
    @Transactional
    public void subscribe(String townLifeId, String userId) {

        TownLife townLife = getTownLifeById(townLifeId);

        townLife.addSubscription(TownLifeSubscription.of(userId));

    }

    @Override
    @Transactional
    public void unsubscribe(String townLifeId, String userId) {

        TownLife townLife = getTownLifeById(townLifeId);

        townLife.removeSubscriptionByUserId(userId);

    }

    private TownLife getTownLifeById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

}
