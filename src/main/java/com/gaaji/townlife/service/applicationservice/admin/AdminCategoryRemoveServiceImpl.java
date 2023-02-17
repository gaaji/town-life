package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.event.CategoryDeletedEvent;
import com.gaaji.townlife.service.event.dto.CategoryDeletedEventBody;
import com.gaaji.townlife.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryRemoveServiceImpl implements AdminCategoryRemoveService {
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void remove(String categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()) return;
        Category category = categoryOptional.get();
        categoryRepository.delete(category);

        eventPublisher.publishEvent(new CategoryDeletedEvent(this, new CategoryDeletedEventBody(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isDefaultCategory()
        )));
    }
}
