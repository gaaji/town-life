package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownLifeRepository extends JpaRepository<TownLife, String> {

    Slice<TownLife> findByTownIdAndIdLessThan(String townId, String id, Pageable pageable);
    Slice<TownLife> findByTownIdAndIdLessThanAndCategoryNotIn(String townId, String id, List<Category> excludedCategories, Pageable pageable);
    Slice<TownLife> findByAuthorIdAndIdLessThan(String authorId, String id, Pageable pageable);

}
