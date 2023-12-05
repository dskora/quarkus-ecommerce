package com.dskora.quarkus.ecommerce.shipment.api.web;

import java.util.UUID;

public class CancelShipmentRequest {
     private UUID orderId;

     public CancelShipmentRequest() {}

     public CancelShipmentRequest(UUID orderId) {
          this.orderId = orderId;
     }

     public UUID getOrderId() {
          return orderId;
     }
}