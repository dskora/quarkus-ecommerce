package com.dskora.quarkus.ecommerce.shipment.api.web;

import java.util.UUID;

public class CompleteShipmentRequest {
     private UUID orderId;
     public CompleteShipmentRequest() {}

     public CompleteShipmentRequest(UUID orderId) {
          this.orderId = orderId;
     }

     public UUID getOrderId() {
          return orderId;
     }
}