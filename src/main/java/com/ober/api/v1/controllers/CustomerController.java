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

@Controller
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
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .map(this::addCustomerUrl)
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                new CustomerListDTO(customers),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerMapper.customerToCustomerDTO(customerService.getCustomerById(id));
        return new ResponseEntity<>(addCustomerUrl(customer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer = customerService.createNewCustomer(customerMapper.customerDtoToCustomer(customerDTO));
        return new ResponseEntity<>(
                addCustomerUrl(customerMapper.customerToCustomerDTO(savedCustomer)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        Customer savedCustomer = customerService.saveCustomer(id, customerMapper.customerDtoToCustomer(customerDTO));
        return new ResponseEntity<>(
                addCustomerUrl(customerMapper.customerToCustomerDTO(savedCustomer)),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        Customer updatedCustomer = customerService.patchCustomer(id, customerMapper.customerDtoToCustomer(customerDTO));
        return new ResponseEntity<>(
                addCustomerUrl(customerMapper.customerToCustomerDTO(updatedCustomer)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private CustomerDTO addCustomerUrl(CustomerDTO customerDTO) {
        customerDTO.setCustomerUrl(BASE_URL + "/" + customerDTO.getId());
        return customerDTO;
    }
}
