package com.dskora.quarkus.ecommerce.shipment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.shipment.api.ShipmentCreatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "shipments")
public class Shipment {
    @Id
    private String id;

    private String name;

    protected Shipment() {}

    protected Shipment(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static ResultWithEvents<Shipment> create(String name)
    {
        Shipment shipment = new Shipment(name);
        ShipmentCreatedEvent event = new ShipmentCreatedEvent(name);

        return new ResultWithEvents<>(shipment, event);
    }

    public String getId() {
        return id;
    }
}
