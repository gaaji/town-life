package com.gaaji.townlife.service.repository;

import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryUnsubscriptionRepository extends JpaRepository<CategoryUnsubscription, String> {

    List<CategoryUnsubscription> findByUserId(String userId);

}
