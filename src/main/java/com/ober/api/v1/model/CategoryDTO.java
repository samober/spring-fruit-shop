package com.ober.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    private String name;

    @JsonProperty("category_url")
    private String categoryUrl;

}
