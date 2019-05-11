package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CategoryMapper;
import com.ober.api.v1.model.CategoryDTO;
import com.ober.api.v1.model.CategoryListDTO;
import com.ober.api.v1.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories()
                .stream()
                .map(this::addCategoryUrl)
                .collect(Collectors.toList());
        return new CategoryListDTO(categories);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return addCategoryUrl(categoryService.getCategoryByName(name));
    }

    private CategoryDTO addCategoryUrl(CategoryDTO categoryDTO) {
        categoryDTO.setCategoryUrl(BASE_URL + "/" + categoryDTO.getId());
        return categoryDTO;
    }

}
