package com.ober.api.v1.services;

import com.ober.api.v1.mappers.CustomerMapper;
import com.ober.api.v1.model.CustomerDTO;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import com.ober.domain.Customer;
import com.ober.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerMapper.customerToCustomerDTO(
                customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new)
        );
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    private CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return saveCustomer(customerDTO);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

           if (customerDTO.getFirstName() != null)
               customer.setFirstName(customerDTO.getFirstName());

           if (customerDTO.getLastName() != null)
               customer.setLastName(customerDTO.getLastName());

           return customerMapper.customerToCustomerDTO(customerRepository.save(customer));

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
