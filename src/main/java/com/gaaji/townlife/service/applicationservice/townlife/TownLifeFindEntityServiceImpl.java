package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeFindEntityServiceImpl implements TownLifeFindEntityService {

    private final TownLifeRepository townLifeRepository;

    @Override
    public TownLife findById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

    @Override
    public Slice<TownLife> findListByTownIdAndIdLessThan(String townId, String offsetTownLifeId, int page, int size) {

        PageRequest paging = PageRequest.of(page, size);
        Slice<TownLife> townLives = townLifeRepository.findByTownIdAndIdLessThan(townId, offsetTownLifeId, paging);

        validateExistTownLives(townLives.getContent());

        return townLives;
    }

    @Override
    public Slice<TownLife> findListByUserIdAndIdLessThan(String userId, String offsetTownLifeId, int page, int size) {

        PageRequest paging = PageRequest.of(page, size);
        Slice<TownLife> townLives = townLifeRepository.findByAuthorIdAndIdLessThan(userId, offsetTownLifeId, paging);

        validateExistTownLives(townLives.getContent());

        return townLives;
    }

    private void validateExistTownLives(List<TownLife> townLives) {
        if(townLives == null || townLives.size() == 0) {
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
        }
    }
}
