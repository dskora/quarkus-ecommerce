package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderCreatedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    private Money total;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, UUID customerId, Money total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
    }
}
