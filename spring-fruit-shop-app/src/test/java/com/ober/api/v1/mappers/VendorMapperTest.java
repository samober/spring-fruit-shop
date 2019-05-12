package com.ober.api.v1.mappers;

import com.ober.api.v1.model.VendorDTO;
import com.ober.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "Fruit Shop";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        // given
        Vendor vendor = Vendor
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        // when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        // then
        assertEquals(ID, vendor.getId());
        assertEquals(NAME, vendor.getName());
    }
}