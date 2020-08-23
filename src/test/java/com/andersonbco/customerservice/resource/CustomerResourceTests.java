package com.andersonbco.customerservice.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.andersonbco.customerservice.entity.Address;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.exceptions.CustomerNotFoundException;
import com.andersonbco.customerservice.service.CustomerService;
import com.github.javafaker.Faker;
import java.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerResource.class)
public class CustomerResourceTests {

  @TestConfiguration
  static class CustomerResourceTestsContextConfig {

    @Bean
    public ModelMapper modelMapper() {
      return new ModelMapper();
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ModelMapper modelMapper;

  @MockBean
  private CustomerService service;

  private Customer mockCustomer;

  @Before
  public void setUp() {
    Faker faker = new Faker();

    Address mockAddress = new Address();
    mockAddress.setId(faker.internet().uuid());
    mockAddress.setNumber(faker.number().toString());
    mockAddress.setStreetAddress(faker.address().streetAddress());
    mockAddress.setCity(faker.address().city());
    mockAddress.setState(faker.address().state());
    mockAddress.setZipCode(faker.address().zipCode());

    mockCustomer = new Customer();
    mockCustomer.setId(faker.internet().uuid());
    mockCustomer.setName(faker.name().name());
    mockCustomer.setCpf(faker.number().toString());
    mockCustomer.setAddresses(Arrays.asList(mockAddress));
  }

  @Test
  public void whenCustomerExists_thenReturnCustomer() throws Exception {

    when(service.findById(mockCustomer.getId())).thenReturn(mockCustomer);

    mockMvc.perform(
        get("/customer/{id}", mockCustomer.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            status().isOk())
        .andExpect(jsonPath("$.id", is(mockCustomer.getId())))
        .andExpect(jsonPath("$.name", is(mockCustomer.getName())))
        .andExpect(jsonPath("$.cpf", is(mockCustomer.getCpf())))
        .andExpect(jsonPath("$.addresses", Matchers.hasSize(1)));
  }

  @Test
  public void whenCustomerNotExists_thenReturn404() throws Exception {

    when(service.findById(mockCustomer.getId())).thenThrow(CustomerNotFoundException.class);

    mockMvc.perform(
        get("/customer/{id}", mockCustomer.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            status().isNotFound())
        .andExpect(jsonPath("$.statusCode", is(404)))
        .andExpect(jsonPath("$.errorMessage", is("Customer not found!")));
  }
}
