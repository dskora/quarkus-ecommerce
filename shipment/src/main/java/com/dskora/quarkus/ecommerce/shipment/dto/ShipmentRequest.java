package com.dskora.quarkus.ecommerce.shipment.dto;

public class ShipmentRequest {
     private String name;

     public ShipmentRequest() {}

     public ShipmentRequest(String name) {
          this.name = name;
     }

     public String getName()
     {
          return name;
     }
}