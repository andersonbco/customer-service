package com.andersonbco.customerservice.service;

import com.andersonbco.customerservice.dto.CustomerDTO;
import com.andersonbco.customerservice.entity.Address;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.exceptions.CustomerNotFoundException;
import com.andersonbco.customerservice.repository.AddressRepository;
import com.andersonbco.customerservice.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private AddressRepository addressRepository;
  private CustomerRepository customerRepository;
  private ModelMapper mapper;

  public CustomerService(AddressRepository addressRepository, CustomerRepository customerRepository, ModelMapper mapper) {
    this.addressRepository = addressRepository;
    this.customerRepository = customerRepository;
    this.mapper = mapper;
  }

  public Customer save(CustomerDTO customerDTO) {
    Customer newCustomer = customerRepository.save(mapper.map(customerDTO, Customer.class));
    customerDTO.getAddresses().forEach(address -> {
      Address newAddress = mapper.map(address, Address.class);
      newAddress.setCustomer(newCustomer);
      addressRepository.save(newAddress);
    });

    return newCustomer;
  }

  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  public Customer findById(String id) {
    Optional<Customer> optCustomer = customerRepository.findById(id);

    return optCustomer.orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));
  }

  public void deleteById(String id) {
    customerRepository.deleteById(id);
  }
}
