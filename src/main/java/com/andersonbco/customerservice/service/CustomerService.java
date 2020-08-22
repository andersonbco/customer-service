package com.andersonbco.customerservice.service;

import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.repository.AddressRepository;
import com.andersonbco.customerservice.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private AddressRepository addressRepository;
  private CustomerRepository customerRepository;

  public CustomerService(AddressRepository addressRepository, CustomerRepository customerRepository) {
    this.addressRepository = addressRepository;
    this.customerRepository = customerRepository;
  }

  public Customer save(Customer customer) {
    customerRepository.save(customer);
    customer.getAddresses().forEach(address -> {
      address.setCustomer(customer);
      addressRepository.save(address);
    });

    return customer;
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }
}
