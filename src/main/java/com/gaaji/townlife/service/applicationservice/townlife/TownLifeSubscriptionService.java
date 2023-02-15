package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSubscribeDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeUnsubscribeDto;

public interface TownLifeSubscriptionService {

    void subscribe(String townLifeId, TownLifeSubscribeDto dto);
    void unsubscribe(String townLifeId, TownLifeUnsubscribeDto dto);

}
