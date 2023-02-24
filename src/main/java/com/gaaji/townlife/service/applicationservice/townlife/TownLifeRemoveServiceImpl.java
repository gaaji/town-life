package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeCounterRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gaaji.townlife.global.utils.validation.ValidateResourceAccess.validateAuthorizationRemoving;

@Service
@RequiredArgsConstructor
public class TownLifeRemoveServiceImpl implements TownLifeRemoveService {

    private final TownLifeRepository townLifeRepository;
    private final TownLifeCounterRepository townLifeCounterRepository;

    @Override
    @Transactional
    public void remove(String authId, String townLifeId) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        validateAuthorizationRemoving(authId, townLife.getAuthorId());

        townLifeCounterRepository.delete(townLife.getTownLifeCounter());
        townLifeRepository.delete(townLife);
    }

}
