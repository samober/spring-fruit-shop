package com.ober.api.v1.services;

import com.ober.api.v1.mappers.VendorMapper;
import com.ober.api.v1.model.VendorDTO;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import com.ober.domain.Vendor;
import com.ober.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorMapper.vendorToVendorDTO(
                vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new)
        );
    }

    @Override
    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO saveVendor(Long id, VendorDTO vendorDTO) {
        vendorDTO.setId(id);
        return saveVendor(vendorDTO);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {

            if (vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());

            return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
