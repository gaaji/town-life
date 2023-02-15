package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.townlife.TownLifeType;

public interface TownLifeSaveService {

    TownLifeDetailDto save(TownLifeType type, TownLifeSaveRequestDto dto);

}
