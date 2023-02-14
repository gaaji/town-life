package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownLifeRepository extends JpaRepository<TownLife, String> {
}
