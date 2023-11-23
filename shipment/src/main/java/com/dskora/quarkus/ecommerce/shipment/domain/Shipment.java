package com.dskora.quarkus.ecommerce.shipment.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.ShipmentRequestedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "shipments")
public class Shipment {
    @Id
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    private ShipmentProvider shipmentProvider;

    private ShipmentState shipmentState;

    public Shipment() {}

    public Shipment(UUID orderId, ShipmentProvider shipmentProvider, ShipmentState shipmentState) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.shipmentProvider = shipmentProvider;
        this.shipmentState = ShipmentState.REQUESTED;
    }

    public static ResultWithEvents<Shipment> request(UUID orderId, ShipmentProvider shipmentProvider, ShipmentState shipmentState)
    {
        Shipment shipment = new Shipment(orderId, shipmentProvider, shipmentState);
        ShipmentRequestedEvent event = new ShipmentRequestedEvent(orderId, shipmentProvider, shipmentState);

        return new ResultWithEvents<>(shipment, event);
    }

    public UUID getId() {
        return id;
    }
}
