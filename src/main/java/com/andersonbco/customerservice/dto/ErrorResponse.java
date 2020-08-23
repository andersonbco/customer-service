package com.andersonbco.customerservice.dto;

import lombok.Data;

@Data
public class ErrorResponse {

  private int statusCode;
  private String errorMessage;
}
