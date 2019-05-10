package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CategoryMapper;
import com.ober.api.v1.model.CategoryDTO;
import com.ober.api.v1.model.CategoryListDTO;
import com.ober.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .map(this::addCategoryUrl)
                .collect(Collectors.toList());
        return new ResponseEntity<CategoryListDTO>(
                new CategoryListDTO(categories),
                HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO category = categoryMapper.categoryToCategoryDTO(categoryService.getCategoryByName(name));
        return new ResponseEntity<CategoryDTO>(addCategoryUrl(category), HttpStatus.OK);
    }

    private CategoryDTO addCategoryUrl(CategoryDTO categoryDTO) {
        categoryDTO.setCategoryUrl(BASE_URL + "/" + categoryDTO.getId());
        return categoryDTO;
    }

}
