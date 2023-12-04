package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterProductStockRequest {
     private UUID productId;

     private int stock;

     public RegisterProductStockRequest() {}

     public RegisterProductStockRequest(UUID productId, int stock) {
          this.productId = productId;
          this.stock = stock;
     }
}