package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListResponseDto;

import java.time.LocalDateTime;

public interface TownLifeFindService {

    TownLifeDetailDto findById(String id);
    TownLifeDetailDto visit(String id);
    TownLifeListResponseDto findListByTownId(String userId, String townId, LocalDateTime requestTime, int page, int size);
    TownLifeListResponseDto findListByUserId(String userId, LocalDateTime requestTime, int page, int size);

}
