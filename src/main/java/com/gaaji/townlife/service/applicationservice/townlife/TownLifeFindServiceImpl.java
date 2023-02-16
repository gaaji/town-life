package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TownLifeFindServiceImpl implements TownLifeFindService {

    private final TownLifeFindEntityService entityService;
    private final TownLifeFindCountService countService;

    @Override
    public TownLifeDetailDto findById(String id) {
        TownLife townLife = entityService.findById(id);

        return TownLifeDetailDto.of(townLife);
    }

    @Override
    @Transactional
    public TownLifeDetailDto visit(String id) {
        TownLife townLife = entityService.findById(id);
        TownLifeCounter townLifeCounter = countService.increaseViewCount(townLife);

        return TownLifeDetailDto.of(townLife, townLifeCounter);
    }

    @Override
    @Transactional
    public List<TownLifeListDto> findListByTownId(String townId, String lastTownLifeId, int size) {
        List<TownLife> townLives;

        if(Objects.equals("-1", lastTownLifeId)) {
            townLives = entityService.findListByTownId(townId, size);
        } else {
            townLives = entityService.findMoreListByTownIdAndIdLessThan(townId, lastTownLifeId, size);
        }

        return convertDtoListFromEntityList(townLives);
    }

    @Override
    @Transactional
    public List<TownLifeListDto> findListByUserId(String userId, String lastTownLifeId, int size) {
        List<TownLife> townLives;

        if(Objects.equals("-1", lastTownLifeId)) {
            townLives = entityService.findListByAuthorId(userId, size);
        } else {
            townLives = entityService.findMoreListByAuthorIdAndIdLessThan(userId, lastTownLifeId, size);
        }

        return convertDtoListFromEntityList(townLives);
    }

    private List<TownLifeListDto> convertDtoListFromEntityList(List<TownLife> townLives) {
        return townLives.stream()
                .map(townLife -> TownLifeListDto.of(townLife, townLife.getTownLifeCounter()))
                .collect(Collectors.toList());
    }
}
