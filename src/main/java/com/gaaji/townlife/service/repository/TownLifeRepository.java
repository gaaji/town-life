package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownLifeRepository extends JpaRepository<TownLife, String> {


    List<TownLife> findByTownId(String townId, Pageable pageable);

    List<TownLife> findByTownIdAndIdLessThan(String townId, String id, Pageable pageable);

    List<TownLife> findByAuthorId(String authorId, Pageable pageable);

    List<TownLife> findByAuthorIdAndIdLessThan(String authorId, String id, Pageable pageable);

}
