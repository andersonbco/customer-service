package com.andersonbco.customerservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class CustomerResponse {
  private String id;
  private String name;
  private String cpf;
  private List<AddressResponse> addresses;
}
