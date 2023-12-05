package com.dskora.quarkus.ecommerce.inventory.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductOutOfStockEvent implements DomainEvent {
    private UUID orderId;

    private UUID productId;

    private int quantity;

    public ProductOutOfStockEvent() {}

    public ProductOutOfStockEvent(UUID orderId, UUID productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
