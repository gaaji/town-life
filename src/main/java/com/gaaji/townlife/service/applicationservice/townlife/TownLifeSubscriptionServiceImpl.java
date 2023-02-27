package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeSubscription;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import com.gaaji.townlife.service.repository.TownLifeSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeSubscriptionServiceImpl implements TownLifeSubscriptionService {

    private final TownLifeRepository townLifeRepository;
    private final TownLifeSubscriptionRepository townLifeSubscriptionRepository;

    @Override
    @Transactional
    public void subscribe(String userId, String townLifeId) {

        TownLife townLife = getTownLifeById(townLifeId);

        TownLifeSubscription subscription = townLifeSubscriptionRepository.save(TownLifeSubscription.of(userId));
        townLife.addSubscription(subscription);
    }

    @Override
    @Transactional
    public void unsubscribe(String userId, String townLifeId) {

        TownLife townLife = getTownLifeById(townLifeId);

        TownLifeSubscription subscription = townLife.removeSubscriptionByUserId(userId);
        townLifeSubscriptionRepository.delete(subscription);
    }

    private TownLife getTownLifeById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

}
