package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CategoryUnsubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategorySubscriptionServiceImpl implements CategorySubscriptionService {

    private final CategoryRepository categoryRepository;
    private final CategoryUnsubscriptionRepository categoryUnsubscriptionRepository;

    @Override
    @Transactional
    public void subscribe(String userId, String categoryId) {
        Category category = getCategoryById(categoryId);

        CategoryUnsubscription unsubscription = category.removeUnsubscriptionByUserId(userId);
        categoryUnsubscriptionRepository.delete(unsubscription);
    }

    @Override
    @Transactional
    public void unsubscribe(String userId, String categoryId) {
        Category category = getCategoryById(categoryId);

        CategoryUnsubscription unsubscription = categoryUnsubscriptionRepository.save(CategoryUnsubscription.of(userId));
        category.addUnsubscription(unsubscription);
    }

    private Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));
    }

}
