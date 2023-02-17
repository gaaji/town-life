package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLifeRepository extends JpaRepository<TownLife, String> {

    Slice<TownLife> findByTownIdAndIdLessThan(String townId, String id, Pageable pageable);
    Slice<TownLife> findByAuthorIdAndIdLessThan(String authorId, String id, Pageable pageable);

}
