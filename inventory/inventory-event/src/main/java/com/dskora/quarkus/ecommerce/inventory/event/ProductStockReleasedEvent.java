package com.dskora.quarkus.ecommerce.inventory.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductStockReleasedEvent implements DomainEvent {
    private UUID orderId;

    public ProductStockReleasedEvent() {}

    public ProductStockReleasedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
