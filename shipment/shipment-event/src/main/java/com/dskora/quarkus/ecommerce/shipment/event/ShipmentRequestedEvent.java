package com.dskora.quarkus.ecommerce.shipment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.shipment.common.ShipmentProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShipmentRequestedEvent implements DomainEvent {
    private UUID orderId;

    private ShipmentProvider shipmentProvider;

    private String address;

    public ShipmentRequestedEvent() {}

    public ShipmentRequestedEvent(UUID orderId, ShipmentProvider shipmentProvider, String address) {
        this.orderId = orderId;
        this.shipmentProvider = shipmentProvider;
        this.address = address;
    }
}
