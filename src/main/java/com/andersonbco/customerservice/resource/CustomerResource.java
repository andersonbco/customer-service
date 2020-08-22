package com.andersonbco.customerservice.resource;

import com.andersonbco.customerservice.dto.CustomerDTO;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.service.CustomerService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerResource {

  private CustomerService service;
  private ModelMapper modelMapper;

  public CustomerResource(CustomerService service, ModelMapper modelMapper) {
    this.service = service;
    this.modelMapper = modelMapper;
  }

  @GetMapping
  public ResponseEntity<List<CustomerDTO>> findAll() {
    return ResponseEntity.ok().body(service.findAll().parallelStream().map(customer -> modelMapper.map(customer, CustomerDTO.class)).collect(
        Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> save(@RequestBody Customer customer) {
    return ResponseEntity.ok().body(modelMapper.map(service.save(customer), CustomerDTO.class));
  }
}