package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListResponseDto;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

        return TownLifeDetailDto.of(townLife);
    }

    @Override
    @Transactional
    public TownLifeDetailDto visit(String id) {

        TownLife townLife = entityService.findById(id);
        TownLifeCounter counter = countService.increaseViewCount(townLife.getTownLifeCounter().getId());

        return TownLifeDetailDto.of(townLife, counter);
    }

    @Override
    @Transactional
    public TownLifeListResponseDto findListByTownId(String townId, LocalDateTime requestTime, int page, int size) {

        String offsetId = new ULID().nextULID(requestTime.toInstant(ZoneOffset.UTC).toEpochMilli());
        Slice<TownLife> townLives = entityService.findListByTownIdAndIdLessThan(townId, offsetId, page, size);

        return TownLifeListResponseDto.of(townLives);
    }

    @Override
    @Transactional
    public TownLifeListResponseDto findListByUserId(String userId, LocalDateTime requestTime, int page, int size) {

        String offsetId = new ULID().nextULID(requestTime.toInstant(ZoneOffset.UTC).toEpochMilli());
        Slice<TownLife> townLives = entityService.findListByUserIdAndIdLessThan(userId, offsetId, page, size);

        return TownLifeListResponseDto.of(townLives);
    }

}
