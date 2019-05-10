package com.ober.bootstrap;

import com.ober.domain.Category;
import com.ober.domain.Customer;
import com.ober.domain.Vendor;
import com.ober.repositories.CategoryRepository;
import com.ober.repositories.CustomerRepository;
import com.ober.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCustomers() {
        Customer sam = Customer.builder().firstName("Sam").lastName("Axe").build();
        Customer bob = Customer.builder().firstName("Bob").lastName("Doe").build();
        Customer sarah = Customer.builder().firstName("Sarah").lastName("Smith").build();

        customerRepository.save(sam);
        customerRepository.save(bob);
        customerRepository.save(sarah);

        log.info("Customers Loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = Category.builder().name("Fruits").build();
        Category dried = Category.builder().name("Dried").build();
        Category fresh = Category.builder().name("Fresh").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category nuts = Category.builder().name("Nuts").build();

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Categories Loaded = " + categoryRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = Vendor.builder().name("Western Tasty Fruits Ltd.").build();
        Vendor vendor2 = Vendor.builder().name("Exotic Fruits Company").build();
        Vendor vendor3 = Vendor.builder().name("Home Fruits").build();
        Vendor vendor4 = Vendor.builder().name("Fun Fresh Fruits Ltd.").build();
        Vendor vendor5 = Vendor.builder().name("Nuts of Nuts Company").build();

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
        vendorRepository.save(vendor5);

        log.info("Vendors Loaded = " + vendorRepository.count());
    }
}
