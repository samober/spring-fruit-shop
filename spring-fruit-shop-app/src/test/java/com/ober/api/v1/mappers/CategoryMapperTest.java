package com.ober.api.v1.mappers;

import com.ober.api.v1.model.CategoryDTO;
import com.ober.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final String NAME = "Joe";
    private static final Long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        // given
        Category category = Category
                .builder()
                .name(NAME)
                .id(ID)
                .build();

        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}