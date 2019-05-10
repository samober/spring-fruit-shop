package com.ober.services;

import com.ober.domain.Vendor;

import java.util.List;

public interface VendorService {

    List<Vendor> getAllVendors();

    Vendor getVendorById(Long id);

    Vendor saveVendor(Vendor vendor);

    Vendor saveVendor(Long id, Vendor vendor);

    Vendor patchVendor(Long id, Vendor vendor);

    void deleteVendorById(Long id);

}
