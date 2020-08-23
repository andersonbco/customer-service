package com.andersonbco.customerservice.resource;

import com.andersonbco.customerservice.dto.CustomerResponse;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.service.CustomerService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/customer")
public class CustomerResource {

  private CustomerService service;
  private ModelMapper modelMapper;

  public CustomerResource(CustomerService service, ModelMapper modelMapper) {
    this.service = service;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> findById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(modelMapper.map(service.findById(id), CustomerResponse.class));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().parallelStream().map(customer -> modelMapper.map(customer, CustomerResponse.class)).collect(
            Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<CustomerResponse> save(@RequestBody Customer customer) {
    Customer newCustomer = service.save(customer);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCustomer.getId())
        .toUri();
    return ResponseEntity.created(location).body(modelMapper.map(newCustomer, CustomerResponse.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CustomerResponse> delete(@PathVariable("id") String id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
