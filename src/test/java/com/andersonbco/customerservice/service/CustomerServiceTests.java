package com.andersonbco.customerservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import com.andersonbco.customerservice.entity.Address;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.exceptions.CustomerNotFoundException;
import com.andersonbco.customerservice.repository.CustomerRepository;
import com.github.javafaker.Faker;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomerServiceTests {

  @MockBean
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  Customer mockCustomer;

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);

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

    when(customerRepository.findById(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));
  }

  @Test
  public void whenCustomerExists_thenReturnCustomer() {

    Customer foundCustomer = customerService.findById(mockCustomer.getId());

    assertNotNull(foundCustomer);
    assertEquals(mockCustomer.getName(), foundCustomer.getName());
    assertEquals(mockCustomer.getCpf(), foundCustomer.getCpf());
    assertEquals(1, foundCustomer.getAddresses().size());
  }

  @Test
  public void whenCustomerDoesNotExists_thenThrowsCustomerNotFoundException() {

    Exception exception = assertThrows(CustomerNotFoundException.class, () -> customerService.findById("12345"));

    assertEquals("Customer not found!", exception.getMessage());
  }
}
