package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.domain.townlife.TownLife;

import java.util.List;

public interface TownLifeFindEntityService {

    TownLife findById(String id);

    List<TownLife> findListByTownId(String townId, int size);

    List<TownLife> findListByTownIdAndIdLessThan(String townId, String lastTownLifeId, int size);

}
