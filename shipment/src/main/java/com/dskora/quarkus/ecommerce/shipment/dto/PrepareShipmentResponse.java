package com.dskora.quarkus.ecommerce.shipment.dto;

import java.util.UUID;

public class PrepareShipmentResponse {
    private UUID id;

    public PrepareShipmentResponse() {}

    public PrepareShipmentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
