package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.ProductRegisteredInStockEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "inventory")
public class Inventory {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID productId;

    private int stocks;

    protected Inventory() {}

    protected Inventory(UUID productId, int stocks) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.stocks = stocks;
    }

    public static ResultWithEvents<Inventory> create(UUID productId, int stocks)
    {
        Inventory inventory = new Inventory(productId, stocks);
        ProductRegisteredInStockEvent event = new ProductRegisteredInStockEvent(productId, stocks);

        return new ResultWithEvents<>(inventory, event);
    }

    public UUID getId() {
        return id;
    }
}
