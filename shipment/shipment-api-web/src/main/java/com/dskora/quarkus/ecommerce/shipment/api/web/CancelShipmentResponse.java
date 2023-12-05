package com.dskora.quarkus.ecommerce.shipment.api.web;

import java.util.UUID;

public class CancelShipmentResponse {
    private UUID id;

    public CancelShipmentResponse() {}

    public CancelShipmentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
