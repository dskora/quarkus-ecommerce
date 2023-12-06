package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReleaseProductStockRequest {
     private UUID orderId;

     private UUID productId;

     private int quantity;

     public ReleaseProductStockRequest() {}

     public ReleaseProductStockRequest(UUID orderId, UUID productId, int quantity) {
          this.orderId = orderId;
          this.productId = productId;
          this.quantity = quantity;
     }
}