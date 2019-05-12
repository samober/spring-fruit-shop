package com.ober.api.v1.services;

import com.ober.api.v1.mappers.CustomerMapper;
import com.ober.api.v1.model.CustomerDTO;
import com.ober.api.v1.services.CustomerService;
import com.ober.api.v1.services.CustomerServiceImpl;
import com.ober.domain.Customer;
import com.ober.repositories.CustomerRepository;
import com.ober.api.v1.services.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME_1 = "Bob";
    private static final String LAST_NAME_1 = "Test";
    private static final String FIRST_NAME_2 = "Sam";
    private static final String LAST_NAME_2 = "Axe";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    public void getAllCustomers() {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        // when
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customersReturned = customerService.getAllCustomers();

        // then
        assertEquals(3, customersReturned.size());
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomerById() {
        // given
        Customer customer = Customer
                .builder()
                .id(ID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .build();

        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        CustomerDTO customerReturned = customerService.getCustomerById(ID);

        // then
        assertNotNull(customerReturned);
        assertEquals(ID, customerReturned.getId());
        assertEquals(FIRST_NAME_1, customerReturned.getFirstName());
        assertEquals(LAST_NAME_1, customerReturned.getLastName());
        verify(customerRepository).findById(anyLong());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCustomerByIdNotFound() throws Exception {
        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // show throw error
        customerService.getCustomerById(1L);
    }

    @Test
    public void createNewCustomer() throws Exception {
        // given
        Customer customer = Customer.builder().id(ID).firstName(FIRST_NAME_1).lastName(LAST_NAME_1).build();
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(FIRST_NAME_1).lastName(LAST_NAME_1).build();

        // when
        when(customerRepository.save(any())).thenReturn(customer);
        CustomerDTO customerReturned = customerService.createNewCustomer(customerDTO);

        // then
        assertNotNull(customerReturned);
        assertEquals(ID, customerReturned.getId());
        assertEquals(FIRST_NAME_1, customerReturned.getFirstName());
        assertEquals(LAST_NAME_1, customerReturned.getLastName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void updateCustomer() throws Exception {
        // given
        Customer customer = Customer.builder()
                .id(ID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .build();
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(ID)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .build();

        // when
        when(customerRepository.save(any())).thenReturn(customer);
        CustomerDTO savedCustomer = customerService.saveCustomer(ID, customerDTO);

        assertNotNull(savedCustomer);
        assertEquals(ID, savedCustomer.getId());
        assertEquals(FIRST_NAME_1, savedCustomer.getFirstName());
        assertEquals(LAST_NAME_1, savedCustomer.getLastName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchCustomerNotFound() throws Exception {
        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // show throw error
        customerService.patchCustomer(1L, new CustomerDTO());
    }

    @Test
    public void deleteCustomerById() throws Exception {
        Long id = 1L;

        customerService.deleteCustomerById(id);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }

}