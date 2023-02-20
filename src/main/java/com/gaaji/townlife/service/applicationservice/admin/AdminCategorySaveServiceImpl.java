package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.global.exception.api.CategoryNameUniqueConstraintException;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.event.CategoryCreatedEvent;
import com.gaaji.townlife.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategorySaveServiceImpl implements AdminCategorySaveService {
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public AdminCategorySaveResponseDto save(AdminCategorySaveRequestDto dto) {
        if(categoryRepository.existsByName(dto.getName())) throw new CategoryNameUniqueConstraintException(dto.getName());
        Category savedCategory = categoryRepository.save(Category.create(dto.getName(), dto.isDefaultCategory(), dto.getDescription()));

        eventPublisher.publishEvent(new CategoryCreatedEvent(this, savedCategory));

        return new AdminCategorySaveResponseDto(savedCategory);
    }
}
