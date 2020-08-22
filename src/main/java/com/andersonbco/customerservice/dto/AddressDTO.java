package com.andersonbco.customerservice.dto;

import lombok.Data;

@Data
public class AddressDTO {
  private String number;
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
}
