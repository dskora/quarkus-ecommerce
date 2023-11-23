package com.dskora.quarkus.ecommerce.shipment.dto;

import java.util.UUID;

public class CreateShipmentResponse {
    private UUID id;

    public CreateShipmentResponse() {}

    public CreateShipmentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
