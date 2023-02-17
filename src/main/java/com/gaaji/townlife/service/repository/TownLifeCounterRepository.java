package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.TownLifeCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLifeCounterRepository extends JpaRepository<TownLifeCounter, String> {
}
