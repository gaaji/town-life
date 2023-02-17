package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeModifyRequestDto;

public interface TownLifeModifyService {

    TownLifeDetailDto modify(String townLifeId, String authorId, TownLifeModifyRequestDto dto);

}
