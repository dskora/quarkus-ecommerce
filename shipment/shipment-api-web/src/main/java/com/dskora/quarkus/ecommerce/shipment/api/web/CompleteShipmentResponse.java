package com.dskora.quarkus.ecommerce.shipment.api.web;

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
