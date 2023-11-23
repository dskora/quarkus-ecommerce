package com.dskora.quarkus.ecommerce.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterProductStockRequest {
     private UUID productId;

     private int stocks;

     public RegisterProductStockRequest() {}

     public RegisterProductStockRequest(UUID productId, int stocks) {
          this.productId = productId;
          this.stocks = stocks;
     }
}