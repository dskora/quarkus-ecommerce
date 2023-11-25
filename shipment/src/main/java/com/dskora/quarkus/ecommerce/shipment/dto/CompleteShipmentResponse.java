package com.dskora.quarkus.ecommerce.shipment.dto;

import java.util.UUID;

public class CompleteShipmentResponse {
    private UUID id;

    public CompleteShipmentResponse() {}

    public CompleteShipmentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
