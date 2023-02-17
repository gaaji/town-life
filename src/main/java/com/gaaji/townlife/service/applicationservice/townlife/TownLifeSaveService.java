package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;

public interface TownLifeSaveService {

    TownLifeDetailDto save(TownLifeSaveRequestDto dto);

}
