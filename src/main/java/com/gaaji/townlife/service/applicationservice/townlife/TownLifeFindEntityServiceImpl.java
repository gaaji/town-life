package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
    public List<TownLife> findListByTownId(String townId, int size) {

        PageRequest page = PageRequest.ofSize(size);
        List<TownLife> townLives = townLifeRepository.findByTownId(townId, page);

        validateExistTownLives(townLives);

        return townLives;
    }

    @Override
    public List<TownLife> findMoreListByTownIdAndIdLessThan(String townId, String lastTownLifeId, int size) {

        PageRequest page = PageRequest.ofSize(size);
        List<TownLife> townLives = townLifeRepository.findByTownIdAndIdLessThan(townId, lastTownLifeId, page);

        validateExistTownLives(townLives);

        return townLives;
    }

    @Override
    public List<TownLife> findListByAuthorId(String authorId, int size) {

        PageRequest page = PageRequest.ofSize(size);
        List<TownLife> townLives = townLifeRepository.findByAuthorId(authorId, page);

        validateExistTownLives(townLives);

        return townLives;
    }

    @Override
    public List<TownLife> findMoreListByAuthorIdAndIdLessThan(String authorId, String lastTownLifeId, int size) {

        PageRequest page = PageRequest.ofSize(size);
        List<TownLife> townLives = townLifeRepository.findByAuthorIdAndIdLessThan(authorId, lastTownLifeId, page);

        validateExistTownLives(townLives);

        return townLives;
    }

    private void validateExistTownLives(List<TownLife> townLives) {
        if(townLives == null || townLives.size() == 0) {
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
        }
    }
}
