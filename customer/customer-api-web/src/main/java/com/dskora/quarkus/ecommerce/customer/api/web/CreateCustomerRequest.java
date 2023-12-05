package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCustomerRequest {
     private String name;
     private BigDecimal creditLimit;

     public CreateCustomerRequest() {}

     public CreateCustomerRequest(String name, BigDecimal creditLimit) {
          this.name = name;
          this.creditLimit = creditLimit;
     }
}