package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceAlreadyExistException;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.global.exception.api.ResourceUnmodifiableException;
import com.gaaji.townlife.service.controller.category.dto.CategorySubscribeDto;
import com.gaaji.townlife.service.controller.category.dto.CategoryUnsubscribeDto;
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
    public void subscribe(String categoryId, CategorySubscribeDto dto) {

    }

    @Override
    @Transactional
    public void unsubscribe(String categoryId, CategoryUnsubscribeDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));

        validateCategoryIsDefault(category);

        validateUserIsAlreadyUnsubscribedCategory(dto.getUserId(), category);

        CategoryUnsubscription categoryUnsubscription = categoryUnsubscriptionRepository.save(CategoryUnsubscription.of(dto.getUserId()));
        categoryUnsubscription.associateCategory(category);
    }

    private void validateCategoryIsDefault(Category category) {
        if (category.isDefault()) {
            throw new ResourceUnmodifiableException(ApiErrorCode.CATEGORY_SUBSCRIPTION_UNMODIFIABLE_ERROR);
        }
    }

    private void validateUserIsAlreadyUnsubscribedCategory(String userId, Category category) {
        if (categoryUnsubscriptionRepository.existsByUserIdAndCategory(userId, category)) {
            throw new ResourceAlreadyExistException(ApiErrorCode.CATEGORY_UNSUBSCRIPTION_ALREADY_EXIST_ERROR);
        }
    }

}
