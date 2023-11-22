package com.dskora.quarkus.ecommerce.shipment.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;

public class ShipmentCreatedEvent implements DomainEvent {
    private String name;

    public ShipmentCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
