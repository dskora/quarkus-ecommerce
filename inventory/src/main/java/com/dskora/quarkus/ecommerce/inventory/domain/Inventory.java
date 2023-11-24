package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreditReservedEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.ProductOutOfStockEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.ProductRegisteredInStockEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.ProductStockReservedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
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

    private int stock;

    protected Inventory() {}

    protected Inventory(UUID productId, int stock) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.stock = stock;
    }

    public static ResultWithEvents<Inventory> create(UUID productId, int stock)
    {
        Inventory inventory = new Inventory(productId, stock);
        ProductRegisteredInStockEvent event = new ProductRegisteredInStockEvent(productId, stock);

        return new ResultWithEvents<>(inventory, event);
    }

    public ResultWithEvents<Inventory> reserveStock(UUID orderId, UUID customerId, int quantity) throws ProductOutOfStockException {
        if (quantity > stock) {
            throw new ProductOutOfStockException();
        }

        this.stock -= quantity;
        ProductStockReservedEvent event = new ProductStockReservedEvent(orderId, customerId);

        return new ResultWithEvents<>(this, event);
    }

    public UUID getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }
}
