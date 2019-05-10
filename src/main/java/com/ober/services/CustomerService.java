package com.ober.services;

import com.ober.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer createNewCustomer(Customer customer);

    Customer saveCustomer(Long id, Customer customer);

    Customer patchCustomer(Long id, Customer customer);

    void deleteCustomerById(Long id);

}
