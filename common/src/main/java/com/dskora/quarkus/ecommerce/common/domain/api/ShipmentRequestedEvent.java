package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentState;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

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
