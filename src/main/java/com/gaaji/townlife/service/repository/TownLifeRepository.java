package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TownLifeRepository extends JpaRepository<TownLife, String> {

    @Override
    @Query(value = "SELECT t FROM TownLife t WHERE t.id = :id AND t.deletedAt is null")
    Optional<TownLife> findById(String id);
    Slice<TownLife> findByTownIdAndIdLessThanAndDeletedAtIsNull(String townId, String id, Pageable pageable);
    Slice<TownLife> findByTownIdAndIdLessThanAndCategoryNotInAndDeletedAtIsNull(String townId, String id, List<Category> excludedCategories, Pageable pageable);
    Slice<TownLife> findByAuthorIdAndIdLessThanAndDeletedAtIsNull(String authorId, String id, Pageable pageable);

}
