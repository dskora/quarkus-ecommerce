package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ReserveCustomerCreditResponse {
     private UUID customerId;

     public ReserveCustomerCreditResponse() {}

     public ReserveCustomerCreditResponse(UUID customerId) {
          this.customerId = customerId;
     }
}