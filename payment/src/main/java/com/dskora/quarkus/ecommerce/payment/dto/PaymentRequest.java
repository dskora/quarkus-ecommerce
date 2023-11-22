package com.dskora.quarkus.ecommerce.payment.dto;

public class PaymentRequest {
     private String name;

     public PaymentRequest() {}

     public PaymentRequest(String name) {
          this.name = name;
     }

     public String getName()
     {
          return name;
     }
}