package com.andersonbco.customerservice.config;

import com.andersonbco.customerservice.dto.ErrorResponse;
import com.andersonbco.customerservice.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomerNotFoundException() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
    errorResponse.setErrorMessage("Customer not found!");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
