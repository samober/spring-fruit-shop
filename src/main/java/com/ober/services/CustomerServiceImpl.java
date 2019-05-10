package com.ober.services;

import com.ober.domain.Customer;
import com.ober.repositories.CustomerRepository;
import com.ober.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    private Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer saveCustomer(Long id, Customer customer) {
        customer.setId(id);
        return saveCustomer(customer);
    }

    @Override
    public Customer patchCustomer(Long id, Customer customer) {
        return customerRepository.findById(id).map(foundCustomer -> {

           if (customer.getFirstName() != null)
               foundCustomer.setFirstName(customer.getFirstName());

           if (customer.getLastName() != null)
               foundCustomer.setLastName(customer.getLastName());

           return customerRepository.save(foundCustomer);

        }).orElseThrow(ResourceNotFoundException::new); // todo implement better error handling
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
