package com.ober.services;

import com.ober.domain.Category;
import com.ober.repositories.CategoryRepository;
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

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void getAllCategories() {
        // given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        // when
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> categoriesReturned = categoryService.getAllCategories();

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
        Category categoryReturned = categoryService.getCategoryByName(NAME);

        // then
        assertNotNull(categoryReturned);
        assertEquals(ID, categoryReturned.getId());
        assertEquals(NAME, categoryReturned.getName());
    }
}