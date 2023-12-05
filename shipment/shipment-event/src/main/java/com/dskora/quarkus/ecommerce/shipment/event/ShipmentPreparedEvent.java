package com.dskora.quarkus.ecommerce.shipment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShipmentPreparedEvent implements DomainEvent {
    private UUID orderId;

    public ShipmentPreparedEvent() {}

    public ShipmentPreparedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
