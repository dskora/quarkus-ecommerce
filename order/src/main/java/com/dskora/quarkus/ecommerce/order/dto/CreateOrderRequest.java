package com.dskora.quarkus.ecommerce.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderRequest {
     private UUID customerId;

     private UUID productId;

     private BigDecimal total;

     public CreateOrderRequest() {}

     public CreateOrderRequest(UUID customerId, UUID productId, BigDecimal total) {
          this.customerId = customerId;
          this.productId = productId;
          this.total = total;
     }
}