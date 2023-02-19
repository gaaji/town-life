package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.domain.Slice;

public interface TownLifeFindEntityService {

    TownLife findById(String id);
    Slice<TownLife> findListByTownIdAndIdLessThan(String userId, String townId, String offsetTownLifeId, int page, int size);
    Slice<TownLife> findListByUserIdAndIdLessThan(String userId, String offsetTownLifeId, int page, int size);

}
