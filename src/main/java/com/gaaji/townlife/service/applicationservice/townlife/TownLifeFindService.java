package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeListDto;

import java.util.List;

public interface TownLifeFindService {

    TownLifeDetailDto findById(String id);
    TownLifeDetailDto visit(String id);
    List<TownLifeListDto> findListByTownId(String townId, String lastTownLifeId, int size);
    List<TownLifeListDto> findListByUserId(String userId);

}
