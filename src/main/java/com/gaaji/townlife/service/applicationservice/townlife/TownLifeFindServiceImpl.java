package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public TownLifeDetailDto visit(String id) {
        TownLife townLife = entityService.findById(id);
        TownLifeCounter townLifeCounter = countService.increaseViewCount(id);

        return TownLifeDetailDto.of(townLife, townLifeCounter);
    }

    @Override
    public List<TownLifeListDto> findListByTownId(String townId, String lastTownLifeId, int size) {
        return null;
    }

    @Override
    public List<TownLifeListDto> findListByUserId(String userId) {
        return null;
    }
}
