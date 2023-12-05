package com.dskora.quarkus.ecommerce.shipment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShipmentCancelledEvent implements DomainEvent {
    private UUID orderId;

    public ShipmentCancelledEvent() {}

    public ShipmentCancelledEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
