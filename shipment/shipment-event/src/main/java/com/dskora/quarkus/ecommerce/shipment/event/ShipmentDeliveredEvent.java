package com.dskora.quarkus.ecommerce.shipment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShipmentDeliveredEvent implements DomainEvent {
    private UUID orderId;

    public ShipmentDeliveredEvent() {}

    public ShipmentDeliveredEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
