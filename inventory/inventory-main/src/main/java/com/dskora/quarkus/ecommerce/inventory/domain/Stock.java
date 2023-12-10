package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import com.dskora.quarkus.ecommerce.inventory.event.ProductRegisteredInStockEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockIncreasedEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReleasedEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReservedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy="stock", cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<StockReservation> reservations = new HashSet<StockReservation>();

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

    public ResultWithEvents<Stock> increaseStock(StockQuantity quantity)
    {
        this.quantity = this.quantity.add(quantity);

        ProductStockIncreasedEvent event = new ProductStockIncreasedEvent(productId, quantity);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Stock> reserveStock(UUID orderId, StockQuantity quantity) throws ProductOutOfStockException {
        if (quantity.isGreaterThan(this.quantity)) {
            throw new ProductOutOfStockException();
        }

        this.quantity = this.quantity.subtract(quantity);
        this.reservations.add(new StockReservation(this, orderId, quantity));

        ProductStockReservedEvent event = new ProductStockReservedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Stock> releaseStock(UUID orderId)
    {
        StockReservation reservation = reservationByOrderId(orderId);

        this.quantity = this.quantity.add(reservation.getQuantity());
        this.reservations.remove(reservationByOrderId(orderId));

        ProductStockReleasedEvent event = new ProductStockReleasedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    private StockReservation reservationByOrderId(UUID orderId) {
        return reservations
            .stream()
            .filter(r -> r.getOrderId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new StockReservationNotFoundException("Cannot find StockReservation by OrderId=" + orderId));
    }
}
