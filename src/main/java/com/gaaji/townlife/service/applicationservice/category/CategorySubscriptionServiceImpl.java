package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.category.dto.CategorySubscribeDto;
import com.gaaji.townlife.service.controller.category.dto.CategoryUnsubscribeDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import com.gaaji.townlife.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategorySubscriptionServiceImpl implements CategorySubscriptionService {

    private final CategoryRepository categoryRepository;

    @Override
    public void subscribe(String categoryId, CategorySubscribeDto dto) {

    }

    @Override
    @Transactional
    public void unsubscribe(String categoryId, CategoryUnsubscribeDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));

        category.addUnsubscription(CategoryUnsubscription.of(dto.getUserId()));
    }


}
