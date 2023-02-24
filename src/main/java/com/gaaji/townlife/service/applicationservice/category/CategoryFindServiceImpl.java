package com.gaaji.townlife.service.applicationservice.category;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.category.dto.CategoryListDto;
import com.gaaji.townlife.service.controller.category.dto.builder.CategoryResponseBuilder;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.category.CategoryUnsubscription;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.CategoryUnsubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryFindServiceImpl implements CategoryFindService {

    private final CategoryRepository categoryRepository;
    private final CategoryUnsubscriptionRepository categoryUnsubscriptionRepository;

    @Override
    @Transactional
    public List<CategoryListDto> findList(String userId) {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new ResourceNotFoundException(ApiErrorCode.CATEGORY_ENTIRE_NOT_FOUND);
        }
        List<CategoryUnsubscription> unsubscriptions = categoryUnsubscriptionRepository.findByUserId(userId);

        return categories.stream()
                .map(category ->
                    CategoryResponseBuilder.categoryListDto(
                            category,
                            unsubscriptions.stream().noneMatch(u -> Objects.equals(category, u.getCategory()))
                    )
                )
                .collect(Collectors.toList());
    }
}
