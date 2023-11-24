package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductStockReservedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    public ProductStockReservedEvent() {}

    public ProductStockReservedEvent(UUID orderId, UUID customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }
}
