package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRejectedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    public OrderRejectedEvent() {}

    public OrderRejectedEvent(UUID orderId, UUID customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }
}
