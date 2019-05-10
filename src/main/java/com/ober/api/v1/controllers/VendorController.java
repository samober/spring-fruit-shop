package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.api.v1.model.VendorListDTO;
import com.ober.domain.Vendor;
import com.ober.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;
    private final VendorMapper vendorMapper;

    public VendorController(VendorService vendorService, VendorMapper vendorMapper) {
        this.vendorService = vendorService;
        this.vendorMapper = vendorMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        // get all vendors from vendor service
        List<Vendor> vendors = vendorService.getAllVendors();
        // convert vendors to vendor DTOs
        List<VendorDTO> vendorDTOs = vendors
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .map(this::addVendorUrl)
                .collect(Collectors.toList());
        // return
        return new VendorListDTO(vendorDTOs);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        // get vendor from vendor service
        Vendor vendor = vendorService.getVendorById(id);
        // convert and add URL
        return addVendorUrl(vendorMapper.vendorToVendorDTO(vendor));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        // convert to domain vendor
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        // save
        Vendor savedVendor = vendorService.saveVendor(vendor);
        // convert to DTO and add URL
        return addVendorUrl(vendorMapper.vendorToVendorDTO(savedVendor));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        // convert to domain Vendor
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        // save
        Vendor savedVendor = vendorService.saveVendor(id, vendor);
        // convert to DTO and add URL
        return addVendorUrl(vendorMapper.vendorToVendorDTO(savedVendor));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        // convert to domain Vendor
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        // patch
        Vendor savedVendor = vendorService.patchVendor(id, vendor);
        // convert to DTO and add URL
        return addVendorUrl(vendorMapper.vendorToVendorDTO(savedVendor));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        // delete
        vendorService.deleteVendorById(id);
    }

    private VendorDTO addVendorUrl(VendorDTO vendorDTO) {
        vendorDTO.setVendorUrl(BASE_URL + "/" + vendorDTO.getId());
        return vendorDTO;
    }
}
