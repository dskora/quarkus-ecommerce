package com.dskora.quarkus.ecommerce.customer.dto;

public class CustomerRequest {
     private String name;

     public CustomerRequest() {}

     public CustomerRequest(String name) {
          this.name = name;
     }

     public String getName()
     {
          return name;
     }
}