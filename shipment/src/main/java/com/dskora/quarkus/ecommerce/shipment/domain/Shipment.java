package com.dskora.quarkus.ecommerce.shipment.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.*;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "shipments")
public class Shipment {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private ShipmentProvider shipmentProvider;

    @Enumerated(EnumType.STRING)
    private ShipmentState shipmentState;

    public Shipment() {}

    public Shipment(UUID orderId, ShipmentProvider shipmentProvider) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.shipmentProvider = shipmentProvider;
        this.shipmentState = ShipmentState.REQUESTED;
    }

    public static ResultWithEvents<Shipment> request(UUID orderId, ShipmentProvider shipmentProvider)
    {
        Shipment shipment = new Shipment(orderId, shipmentProvider);
        ShipmentRequestedEvent event = new ShipmentRequestedEvent(orderId, shipmentProvider);

        return new ResultWithEvents<>(shipment, event);
    }

    public ResultWithEvents<Shipment> complete()
    {
        if (shipmentState == ShipmentState.DELIVERED || shipmentState != ShipmentState.REQUESTED) {
            return new ResultWithEvents<>(this);
        }

        this.shipmentState = ShipmentState.DELIVERED;
        ShipmentDeliveredEvent event = new ShipmentDeliveredEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Shipment> cancel()
    {
        if (shipmentState == ShipmentState.CANCELLED || shipmentState != ShipmentState.REQUESTED) {
            return new ResultWithEvents<>(this);
        }

        this.shipmentState = ShipmentState.CANCELLED;
        ShipmentCancelledEvent event = new ShipmentCancelledEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public UUID getId() {
        return id;
    }
}
