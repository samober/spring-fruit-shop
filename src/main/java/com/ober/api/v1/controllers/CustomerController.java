package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CustomerMapper;
import com.ober.api.v1.model.CustomerDTO;
import com.ober.api.v1.model.CustomerListDTO;
import com.ober.domain.Customer;
import com.ober.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .map(this::addCustomerUrl)
                .collect(Collectors.toList());
        return new CustomerListDTO(customers);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerMapper.customerToCustomerDTO(customerService.getCustomerById(id));
        return addCustomerUrl(customer);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer = customerService.createNewCustomer(customerMapper.customerDtoToCustomer(customerDTO));
        return addCustomerUrl(customerMapper.customerToCustomerDTO(savedCustomer));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        Customer savedCustomer = customerService.saveCustomer(id, customerMapper.customerDtoToCustomer(customerDTO));
        return addCustomerUrl(customerMapper.customerToCustomerDTO(savedCustomer));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        Customer updatedCustomer = customerService.patchCustomer(id, customerMapper.customerDtoToCustomer(customerDTO));
        return addCustomerUrl(customerMapper.customerToCustomerDTO(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

    private CustomerDTO addCustomerUrl(CustomerDTO customerDTO) {
        customerDTO.setCustomerUrl(BASE_URL + "/" + customerDTO.getId());
        return customerDTO;
    }
}
