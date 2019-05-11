package com.ober.api.v1.services;

import com.ober.api.v1.mappers.CategoryMapper;
import com.ober.api.v1.model.CategoryDTO;
import com.ober.domain.Category;
import com.ober.repositories.CategoryRepository;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null)
            return categoryMapper.categoryToCategoryDTO(category);
        else
            throw new ResourceNotFoundException();
    }

}
