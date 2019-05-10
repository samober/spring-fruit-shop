package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.domain.Vendor;
import com.ober.services.VendorService;
import com.ober.services.exceptions.ResourceNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Fruit Shop";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Mock
    VendorService vendorService;

    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorController = new VendorController(vendorService, vendorMapper);

        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        // given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        // when
        when(vendorService.getAllVendors()).thenReturn(vendors);

        // then
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
        verify(vendorService, times(1)).getAllVendors();
    }

    @Test
    public void testGetById() throws Exception {
        // given
        Vendor vendor = Vendor
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        // then
        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).getVendorById(anyLong());
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        // when
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        // then
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(vendorService, times(1)).getVendorById(anyLong());
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        Vendor returnVendor = new Vendor();
        returnVendor.setId(ID);
        returnVendor.setName(NAME);

        // when
        when(vendorService.saveVendor(any())).thenReturn(returnVendor);

        // then
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).saveVendor(any(Vendor.class));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        // when
        when(vendorService.saveVendor(anyLong(), any())).thenReturn(savedVendor);

        // then
        mockMvc.perform(put(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).saveVendor(anyLong(), any(Vendor.class));
    }

    @Test
    public void testPatchVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        // when
        when(vendorService.patchVendor(anyLong(), any())).thenReturn(savedVendor);

        // then
        mockMvc.perform(patch(VendorController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).patchVendor(anyLong(), any(Vendor.class));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

}