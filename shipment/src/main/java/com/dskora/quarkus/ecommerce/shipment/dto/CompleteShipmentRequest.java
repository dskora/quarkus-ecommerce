package com.dskora.quarkus.ecommerce.shipment.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentState;

import java.util.UUID;

public class CompleteShipmentRequest {
     private UUID orderId;

     private ShipmentProvider shipmentProvider;

     private ShipmentState shipmentState;

     public CompleteShipmentRequest() {}

     public CompleteShipmentRequest(UUID orderId, ShipmentProvider shipmentProvider, ShipmentState shipmentState) {
          this.orderId = orderId;
          this.shipmentProvider = shipmentProvider;
          this.shipmentState = shipmentState;
     }

     public UUID getOrderId() {
          return orderId;
     }

     public ShipmentProvider getShipmentProvider() {
          return shipmentProvider;
     }

     public ShipmentState getShipmentState() {
          return shipmentState;
     }
}