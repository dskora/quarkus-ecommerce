package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterProductStockRequest {
     private UUID productId;

     private int quantity;

     public RegisterProductStockRequest() {}

     public RegisterProductStockRequest(UUID productId, int quantity) {
          this.productId = productId;
          this.quantity = quantity;
     }
}