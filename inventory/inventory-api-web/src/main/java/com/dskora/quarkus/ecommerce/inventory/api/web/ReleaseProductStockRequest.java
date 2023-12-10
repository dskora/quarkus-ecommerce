package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReleaseProductStockRequest {
     private UUID orderId;

     private UUID productId;

     public ReleaseProductStockRequest() {}

     public ReleaseProductStockRequest(UUID orderId, UUID productId) {
          this.orderId = orderId;
          this.productId = productId;
     }
}