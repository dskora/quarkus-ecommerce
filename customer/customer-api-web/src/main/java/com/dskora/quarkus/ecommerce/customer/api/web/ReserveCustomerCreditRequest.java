package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ReserveCustomerCreditRequest {
     private UUID orderId;
     private UUID customerId;
     private BigDecimal amount;

     public ReserveCustomerCreditRequest() {}

     public ReserveCustomerCreditRequest(UUID orderId, UUID customerId, BigDecimal amount) {
          this.orderId = orderId;
          this.customerId = customerId;
          this.amount = amount;
     }
}