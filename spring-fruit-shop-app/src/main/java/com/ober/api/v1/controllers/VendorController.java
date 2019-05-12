package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.api.v1.model.VendorListDTO;
import com.ober.domain.Vendor;
import com.ober.api.v1.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOs = vendorService.getAllVendors()
                .stream()
                .map(this::addVendorUrl)
                .collect(Collectors.toList());
        // return
        return new VendorListDTO(vendorDTOs);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return addVendorUrl(vendorService.getVendorById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return addVendorUrl(vendorService.saveVendor(vendorDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        return addVendorUrl(vendorService.saveVendor(id, vendorDTO));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        return addVendorUrl(vendorService.patchVendor(id, vendorDTO));
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
