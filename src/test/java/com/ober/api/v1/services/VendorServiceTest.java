package com.ober.api.v1.services;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.api.v1.services.VendorService;
import com.ober.api.v1.services.VendorServiceImpl;
import com.ober.domain.Vendor;
import com.ober.repositories.VendorRepository;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Fresh Fruits";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        // given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        // when
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorsReturned = vendorService.getAllVendors();

        // then
        assertEquals(3, vendorsReturned.size());
        verify(vendorRepository, times(1)).findAll();
    }

    @Test
    public void getVendorById() {
        // given
        Vendor vendor = Vendor
                .builder()
                .id(ID)
                .name(NAME)
                .build();

        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO vendorReturned = vendorService.getVendorById(ID);

        // then
        assertNotNull(vendorReturned);
        assertEquals(ID, vendorReturned.getId());
        assertEquals(NAME, vendorReturned.getName());
        verify(vendorRepository, times(1)).findById(anyLong());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // should throw error
        vendorService.getVendorById(1L);
    }

    @Test
    public void saveVendor() {
        // given
        Vendor vendor = Vendor
                .builder()
                .id(ID)
                .name(NAME)
                .build();
        VendorDTO vendorDTO = VendorDTO
                .builder()
                .name(NAME)
                .build();

        // when
        when(vendorRepository.save(any())).thenReturn(vendor);
        VendorDTO vendorReturned = vendorService.saveVendor(vendorDTO);

        // then
        assertNotNull(vendorReturned);
        assertEquals(ID, vendorReturned.getId());
        assertEquals(NAME, vendorReturned.getName());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test
    public void saveVendorById() {
        // given
        Vendor vendor = Vendor
                .builder()
                .id(ID)
                .name(NAME)
                .build();
        VendorDTO vendorDTO = VendorDTO
                .builder()
                .name(NAME)
                .build();

        // when
        when(vendorRepository.save(any())).thenReturn(vendor);
        VendorDTO vendorReturned = vendorService.saveVendor(ID, vendorDTO);

        // then
        assertNotNull(vendorReturned);
        assertEquals(ID, vendorReturned.getId());
        assertEquals(NAME, vendorReturned.getName());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchVendorNotFound() {
        // when
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // should throw error
        vendorService.patchVendor(1L, new VendorDTO());
    }

    @Test
    public void deleteVendorById() {
        vendorService.deleteVendorById(1L);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}