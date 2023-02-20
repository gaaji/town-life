package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.utils.ULIDGenerator;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListResponseDto;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeFindServiceImpl implements TownLifeFindService {

    private final TownLifeFindEntityService entityService;
    private final TownLifeFindCountService countService;

    @Override
    @Transactional
    public TownLifeDetailDto findById(String id) {
        TownLife townLife = entityService.findById(id);
        //TODO get auth profile

        return TownLifeDetailDto.of(townLife);
    }

    @Override
    @Transactional
    public TownLifeDetailDto visit(String id) {

        TownLife townLife = entityService.findById(id);
        //TODO get auth profile
        TownLifeCounter counter = countService.increaseViewCount(townLife.getTownLifeCounter().getId());

        return TownLifeDetailDto.of(townLife, counter);
    }

    @Override
    @Transactional
    public TownLifeListResponseDto findListByTownId(String userId, String townId, LocalDateTime requestTime, int page, int size) {

        String offsetId = ULIDGenerator.newULIDByRequestTime(requestTime);
        Slice<TownLife> townLives = entityService.findListByTownIdAndIdLessThan(userId, townId, offsetId, page, size);

        return TownLifeListResponseDto.of(townLives);
    }

    @Override
    @Transactional
    public TownLifeListResponseDto findListByUserId(String userId, LocalDateTime requestTime, int page, int size) {

        String offsetId = ULIDGenerator.newULIDByRequestTime(requestTime);
        Slice<TownLife> townLives = entityService.findListByUserIdAndIdLessThan(userId, offsetId, page, size);

        return TownLifeListResponseDto.of(townLives);
    }

}
