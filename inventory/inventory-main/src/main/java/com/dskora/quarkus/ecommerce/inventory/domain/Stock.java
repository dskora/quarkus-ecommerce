package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.inventory.event.ProductRegisteredInStockEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReleasedEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReservedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity(name = "inventory_stock")
public class Stock {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID productId;

    private int quantity;

    protected Stock() {}

    protected Stock(UUID productId, int quantity) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.quantity = quantity;
    }

    public static ResultWithEvents<Stock> create(UUID productId, int quantity)
    {
        Stock stock = new Stock(productId, quantity);
        ProductRegisteredInStockEvent event = new ProductRegisteredInStockEvent(productId, quantity);

        return new ResultWithEvents<>(stock, event);
    }

    public ResultWithEvents<Stock> reserveStock(UUID orderId, int quantity) throws ProductOutOfStockException {
        if (quantity > this.quantity) {
            throw new ProductOutOfStockException();
        }

        this.quantity -= quantity;
        ProductStockReservedEvent event = new ProductStockReservedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Stock> releaseStock(UUID orderId, int quantity)
    {
        this.quantity += quantity;
        ProductStockReleasedEvent event = new ProductStockReleasedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }
}
