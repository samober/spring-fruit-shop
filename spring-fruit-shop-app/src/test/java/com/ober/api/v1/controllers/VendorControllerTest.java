package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.domain.Vendor;
import com.ober.api.v1.services.VendorService;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Fruit Shop";

    @Mock
    VendorService vendorService;

    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorController = new VendorController(vendorService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        // given
        List<VendorDTO> vendors = Arrays.asList(new VendorDTO(), new VendorDTO(), new VendorDTO());

        // when
        when(vendorService.getAllVendors()).thenReturn(vendors);

        // then
        mockMvc.perform(get(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
        verify(vendorService, times(1)).getAllVendors();
    }

    @Test
    public void testGetById() throws Exception {
        // given
        VendorDTO vendor = VendorDTO
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        // then
        mockMvc.perform(get(VendorController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
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
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(vendorService, times(1)).getVendorById(anyLong());
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO returnVendor = new VendorDTO();
        returnVendor.setId(ID);
        returnVendor.setName(NAME);

        // when
        when(vendorService.saveVendor(any())).thenReturn(returnVendor);

        // then
        mockMvc.perform(post(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).saveVendor(any(VendorDTO.class));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        // when
        when(vendorService.saveVendor(anyLong(), any())).thenReturn(savedVendor);

        // then
        mockMvc.perform(put(VendorController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).saveVendor(anyLong(), any(VendorDTO.class));
    }

    @Test
    public void testPatchVendor() throws Exception {
        // given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME);

        VendorDTO savedVendor = new VendorDTO();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        // when
        when(vendorService.patchVendor(anyLong(), any())).thenReturn(savedVendor);

        // then
        mockMvc.perform(patch(VendorController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/" + ID)));
        verify(vendorService, times(1)).patchVendor(anyLong(), any(VendorDTO.class));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

}