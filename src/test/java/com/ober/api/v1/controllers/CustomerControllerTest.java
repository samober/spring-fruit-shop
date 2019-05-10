package com.ober.api.v1.controllers;

import com.ober.api.v1.mappers.CustomerMapper;
import com.ober.api.v1.model.CustomerDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest extends AbstractRestControllerTest {

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
        mockMvc.perform(get(CustomerController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
        verify(customerService, times(1)).getAllCustomers();
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
        mockMvc.perform(get(CustomerController.BASE_URL + "/" + ID_1)
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + ID_1)));
        verify(customerService, times(1)).getCustomerById(anyLong());
    }

    @Test
    public void createNewCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME_1);
        customer.setLastName(LAST_NAME_1);

        Customer returnCustomer = new Customer();
        returnCustomer.setId(ID_1);
        returnCustomer.setFirstName(FIRST_NAME_1);
        returnCustomer.setLastName(LAST_NAME_1);

        // when
        when(customerService.createNewCustomer(any())).thenReturn(returnCustomer);

        // then
        mockMvc.perform(post(CustomerController.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + ID_1)));
        verify(customerService, times(1)).createNewCustomer(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME_1);
        customer.setLastName(LAST_NAME_1);

        Customer savedCustomer = Customer.builder()
                .id(ID_1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .build();

        // when
        when(customerService.saveCustomer(anyLong(), any(Customer.class))).thenReturn(savedCustomer);

        // then
        mockMvc.perform(put(CustomerController.BASE_URL + "/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + ID_1)));
        verify(customerService).saveCustomer(anyLong(), any(Customer.class));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        // given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRST_NAME_1);

        Customer returnCustomer = new Customer();
        returnCustomer.setId(ID_1);
        returnCustomer.setFirstName(customer.getFirstName());
        returnCustomer.setLastName(LAST_NAME_1);

        // when
        when(customerService.patchCustomer(anyLong(), any(Customer.class))).thenReturn(returnCustomer);

        // then
        mockMvc.perform(patch(CustomerController.BASE_URL + "/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", equalTo(FIRST_NAME_1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + ID_1)));
        verify(customerService, times(1)).patchCustomer(anyLong(), any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

}