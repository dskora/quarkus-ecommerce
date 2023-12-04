package com.dskora.quarkus.ecommerce.shipment.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateShipmentRequest {
     private UUID orderId;

     private ShipmentProvider shipmentProvider;

     private String address;

     public CreateShipmentRequest() {}

     public CreateShipmentRequest(UUID orderId, ShipmentProvider shipmentProvider, String address) {
          this.orderId = orderId;
          this.shipmentProvider = shipmentProvider;
          this.address = address;
     }
}