package com.dskora.quarkus.ecommerce.shipment.dto;

import java.util.UUID;

public class ShipmentResponse {
    private String id;

    public ShipmentResponse() {}

    public ShipmentResponse(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
