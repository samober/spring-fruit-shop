package com.ober.bootstrap;

import com.ober.domain.Category;
import com.ober.domain.Customer;
import com.ober.repositories.CategoryRepository;
import com.ober.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
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
}
