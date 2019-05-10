package com.ober.services;

import com.ober.bootstrap.Bootstrap;
import com.ober.domain.Vendor;
import com.ober.repositories.CategoryRepository;
import com.ober.repositories.CustomerRepository;
import com.ober.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading vendor data...");
        System.out.println(vendorRepository.findAll().size());

        // setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void patchVendorUpdateName() throws Exception {
        String updatedName = "UpdatedName";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);

        // save the original name
        String originalName = originalVendor.getName();

        Vendor vendor = new Vendor();
        vendor.setName(updatedName);

        vendorService.patchVendor(id, vendor);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertNotNull(updatedVendor);
        assertEquals(updatedName, updatedVendor.getName());
        assertNotEquals(originalName, updatedVendor.getName());
    }

    private Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();

        System.out.println("Vendor found: " + vendors.size());

        // return the first ID value
        return vendors.get(0).getId();
    }


}
