package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;

public interface TownLifeFindCountService {

    TownLifeCounter increaseViewCount(TownLife townLife);

}
