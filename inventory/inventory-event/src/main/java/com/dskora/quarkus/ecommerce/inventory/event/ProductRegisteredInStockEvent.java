package com.dskora.quarkus.ecommerce.inventory.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductRegisteredInStockEvent implements DomainEvent {
    private UUID productId;

    private int quantity;

    public ProductRegisteredInStockEvent() {}

    public ProductRegisteredInStockEvent(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
