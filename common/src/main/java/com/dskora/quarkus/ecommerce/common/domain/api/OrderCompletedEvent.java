package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderCompletedEvent implements DomainEvent {
    private UUID orderId;

    public OrderCompletedEvent() {}

    public OrderCompletedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
