package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentState;
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
