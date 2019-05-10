package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CategoryMapper;
import com.ober.domain.Category;
import com.ober.services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String NAME_1 = "Bob";
    private static final String NAME_2 = "Sam";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Mock
    CategoryService categoryService;

    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryController = new CategoryController(categoryService, categoryMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testListCategories() throws Exception {
        // given
        Category category1 = Category.builder().id(ID_1).name(NAME_1).build();
        Category category2 = Category.builder().id(ID_2).name(NAME_2).build();

        List<Category> categories = Arrays.asList(category1, category2);

        // when
        when(categoryService.getAllCategories()).thenReturn(categories);

        // then
        mockMvc.perform(get(CategoryController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetByName() throws Exception {
        // given
        Category category = Category.builder().id(ID_1).name(NAME_1).build();

        // when
        when(categoryService.getCategoryByName(anyString())).thenReturn(category);

        // then
        mockMvc.perform(get(CategoryController.BASE_URL + "/" + NAME_1)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME_1)))
                .andExpect(jsonPath("$.category_url", equalTo(CategoryController.BASE_URL + "/" + ID_1)));
        verify(categoryService, times(1)).getCategoryByName(anyString());
    }

}