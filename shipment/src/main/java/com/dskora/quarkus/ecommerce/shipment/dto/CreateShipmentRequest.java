package com.dskora.quarkus.ecommerce.shipment.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateShipmentRequest {
     private UUID orderId;

     private ShipmentProvider shipmentProvider;

     public CreateShipmentRequest() {}

     public CreateShipmentRequest(UUID orderId, ShipmentProvider shipmentProvider) {
          this.orderId = orderId;
          this.shipmentProvider = shipmentProvider;
     }
}