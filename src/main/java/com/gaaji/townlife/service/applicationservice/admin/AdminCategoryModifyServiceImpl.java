package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryModifyDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.event.CategoryUpdatedEvent;
import com.gaaji.townlife.service.event.dto.CategoryUpdatedEventBody;
import com.gaaji.townlife.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryModifyServiceImpl implements AdminCategoryModifyService {
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void modify(String categoryId, AdminCategoryModifyDto dto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.CATEGORY_NOT_FOUND));
        Category before = Category.create(category.getName(), category.isDefaultCategory(), category.getDescription(), category.getTownLifeType());
        category.modify(dto.getName(), dto.getDescription(), dto.isDefaultCategory());

        Function<Category, CategoryUpdatedEventBody.CategoryDto> createEventDtoFunc = c ->
                new CategoryUpdatedEventBody.CategoryDto(c.getName(), c.getDescription(), c.isDefaultCategory());
        eventPublisher.publishEvent(new CategoryUpdatedEvent(this, new CategoryUpdatedEventBody(
                category.getId(),
                createEventDtoFunc.apply(before),
                createEventDtoFunc.apply(category)
        )));
    }

}
