package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryListDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCategoryFindServiceImpl implements AdminCategoryFindService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<AdminCategoryListDto> findList() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new AdminCategoryListDto(
                category.getId(), category.getName(), category.getDescription(), category.isDefaultCategory())).collect(Collectors.toList()
        );
    }
}
