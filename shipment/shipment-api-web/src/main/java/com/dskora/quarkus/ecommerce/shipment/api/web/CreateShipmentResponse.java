package com.dskora.quarkus.ecommerce.shipment.api.web;

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
