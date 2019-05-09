package com.ober.services;

import com.ober.domain.Customer;
import com.ober.repositories.CustomerRepository;
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
    private static final String FIRST_NAME = "Bob";
    private static final String LAST_NAME = "Test";

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void getAllCustomers() {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        // when
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> customersReturned = customerService.getAllCustomers();

        // then
        assertEquals(3, customersReturned.size());
    }

    @Test
    public void getCustomerById() {
        // given
        Customer customer = Customer
                .builder()
                .id(ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        // when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer customerReturned = customerService.getCustomerById(ID);

        // then
        assertNotNull(customerReturned);
        assertEquals(ID, customerReturned.getId());
        assertEquals(FIRST_NAME, customerReturned.getFirstName());
        assertEquals(LAST_NAME, customerReturned.getLastName());
    }
}