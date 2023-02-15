package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TownLifeFindEntityServiceImpl implements TownLifeFindEntityService {

    private final TownLifeRepository townLifeRepository;

    @Override
    public TownLife findById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }
}
