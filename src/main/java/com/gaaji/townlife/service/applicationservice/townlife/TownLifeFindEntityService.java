package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.domain.townlife.TownLife;

import java.util.List;

public interface TownLifeFindEntityService {

    TownLife findById(String id);

    List<TownLife> findListByTownId(String townId, int size);

    List<TownLife> findMoreListByTownIdAndIdLessThan(String townId, String lastTownLifeId, int size);

    List<TownLife> findListByAuthorId(String authorId, int size);

    List<TownLife> findMoreListByAuthorIdAndIdLessThan(String authorId, String lastTownLifeId, int size);

}
