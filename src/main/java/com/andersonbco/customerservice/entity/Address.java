package com.andersonbco.customerservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  private String number;
  private String streetAddress;
  private String city;
  private String state;
  private String zipCode;
  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "customer_id"
  )
  private Customer customer;
}
