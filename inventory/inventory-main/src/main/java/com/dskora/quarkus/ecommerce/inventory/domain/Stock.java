package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import com.dskora.quarkus.ecommerce.inventory.event.ProductRegisteredInStockEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReleasedEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReservedEvent;
import jakarta.persistence.Embedded;
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

    @Embedded
    private StockQuantity quantity;

    protected Stock() {}

    protected Stock(UUID productId, StockQuantity quantity) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.quantity = quantity;
    }

    public static ResultWithEvents<Stock> create(UUID productId, StockQuantity quantity)
    {
        Stock stock = new Stock(productId, quantity);
        ProductRegisteredInStockEvent event = new ProductRegisteredInStockEvent(productId, quantity);

        return new ResultWithEvents<>(stock, event);
    }

    public ResultWithEvents<Stock> reserveStock(UUID orderId, StockQuantity quantity) throws ProductOutOfStockException {
        if (quantity.isGreaterThan(this.quantity)) {
            throw new ProductOutOfStockException();
        }

        this.quantity = this.quantity.subtract(quantity);
        ProductStockReservedEvent event = new ProductStockReservedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Stock> releaseStock(UUID orderId, StockQuantity quantity)
    {
        this.quantity = this.quantity.add(quantity);
        ProductStockReleasedEvent event = new ProductStockReleasedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }
}
