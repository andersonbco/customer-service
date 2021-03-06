package com.andersonbco.customerservice.resource;

import com.andersonbco.customerservice.dto.CustomerDTO;
import com.andersonbco.customerservice.entity.Customer;
import com.andersonbco.customerservice.service.CustomerService;
import io.swagger.annotations.Api;
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

@Api
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
  public ResponseEntity<CustomerDTO> findById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(modelMapper.map(service.findById(id), CustomerDTO.class));
  }

  @GetMapping
  public ResponseEntity<List<CustomerDTO>> findAll() {
    return ResponseEntity.ok()
        .body(service.findAll().parallelStream().map(customer -> modelMapper.map(customer, CustomerDTO.class)).collect(
            Collectors.toList()));
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO) {
    Customer newCustomer = service.save(customerDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCustomer.getId())
        .toUri();
    return ResponseEntity.created(location).body(modelMapper.map(newCustomer, CustomerDTO.class));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CustomerDTO> delete(@PathVariable("id") String id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
