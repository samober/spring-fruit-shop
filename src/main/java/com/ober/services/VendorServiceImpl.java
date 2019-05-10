package com.ober.services;

import com.ober.domain.Vendor;
import com.ober.repositories.VendorRepository;
import com.ober.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor saveVendor(Long id, Vendor vendor) {
        vendor.setId(id);
        return saveVendor(vendor);
    }

    @Override
    public Vendor patchVendor(Long id, Vendor vendor) {
        return vendorRepository.findById(id).map(foundVendor -> {

            if (vendor.getName() != null)
                foundVendor.setName(vendor.getName());

            return vendorRepository.save(foundVendor);

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
