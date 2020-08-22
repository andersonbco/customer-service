package com.andersonbco.customerservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class CustomerDTO {
  private String id;
  private String name;
  private String cpf;
  private List<AddressDTO> addresses;
}
