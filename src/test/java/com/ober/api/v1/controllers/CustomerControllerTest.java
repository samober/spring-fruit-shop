package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CustomerMapper;
import com.ober.domain.Customer;
import com.ober.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String FIRST_NAME_1 = "Bob";
    private static final String FIRST_NAME_2 = "Sam";
    private static final String LAST_NAME_1 = "Tester";
    private static final String LAST_NAME_2 = "Fisher";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    CustomerService customerService;

    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerController = new CustomerController(customerService, customerMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        // when
        when(customerService.getAllCustomers()).thenReturn(customers);

        // then
        mockMvc.perform(get("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void testGetById() throws Exception {
        // given
        Customer customer = Customer.builder()
                .id(ID_1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .build();

        // when
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        // then
        mockMvc.perform(get("/api/v1/customers/" + ID_1)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME_1)));
    }

}