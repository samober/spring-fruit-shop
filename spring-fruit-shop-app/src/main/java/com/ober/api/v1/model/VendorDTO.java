package com.ober.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {

    private Long id;
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;

}
