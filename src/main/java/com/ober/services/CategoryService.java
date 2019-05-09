package com.ober.services;

import com.ober.domain.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryByName(String name);

}
