package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TownLifeCounterRepository extends JpaRepository<TownLifeCounter, String> {

    Optional<TownLifeCounter> findByTownLife(TownLife townLife);

}
