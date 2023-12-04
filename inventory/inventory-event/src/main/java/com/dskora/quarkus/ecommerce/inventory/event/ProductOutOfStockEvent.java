package com.dskora.quarkus.ecommerce.inventory.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductOutOfStockEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    private int stock;

    public ProductOutOfStockEvent() {}

    public ProductOutOfStockEvent(UUID orderId, UUID customerId, int stock) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.stock = stock;
    }
}
