package com.dskora.quarkus.ecommerce.inventory.dto;

public class InventoryRequest {
     private String name;

     public InventoryRequest() {}

     public InventoryRequest(String name) {
          this.name = name;
     }

     public String getName()
     {
          return name;
     }
}