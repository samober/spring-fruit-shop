package com.ober.api.v1.services;

import com.ober.api.v1.mappers.CategoryMapper;
import com.ober.api.v1.model.CategoryDTO;
import com.ober.api.v1.services.CategoryService;
import com.ober.api.v1.services.CategoryServiceImpl;
import com.ober.domain.Category;
import com.ober.repositories.CategoryRepository;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private static final Long ID = 2L;
    private static final String NAME = "Jimmy";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    public void getAllCategories() {
        // given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        // when
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoriesReturned = categoryService.getAllCategories();

        // then
        assertEquals(3, categoriesReturned.size());
    }

    @Test
    public void getCategoryByName() {
        // given
        Category category = Category
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        when(categoryRepository.findByName(anyString())).thenReturn(category);
        CategoryDTO categoryReturned = categoryService.getCategoryByName(NAME);

        // then
        assertNotNull(categoryReturned);
        assertEquals(ID, categoryReturned.getId());
        assertEquals(NAME, categoryReturned.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCategoryByNameNotFound() {
        // when
        when(categoryRepository.findByName(anyString())).thenReturn(null);

        // should throw error
        categoryService.getCategoryByName("Foo");
    }
}