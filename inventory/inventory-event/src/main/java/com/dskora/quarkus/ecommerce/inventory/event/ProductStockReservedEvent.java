package com.dskora.quarkus.ecommerce.inventory.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductStockReservedEvent implements DomainEvent {
    private UUID orderId;

    public ProductStockReservedEvent() {}

    public ProductStockReservedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
