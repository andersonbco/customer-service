package com.andersonbco.customerservice.exceptions;

public class CustomerNotFoundException extends RuntimeException{

  public CustomerNotFoundException(String message) {
    super(message);
  }
}
