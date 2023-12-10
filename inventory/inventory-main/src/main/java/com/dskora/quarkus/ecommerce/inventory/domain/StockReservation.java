package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity(name = "inventory_stock_reservation")
public class StockReservation {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    @Embedded
    private StockQuantity quantity;

    @ManyToOne
    @JoinColumn(name="stock_id", nullable=false)
    private Stock stock;

    protected StockReservation() {}

    protected StockReservation(Stock stock, UUID orderId, StockQuantity quantity) {
        this.id = UUID.randomUUID();
        this.stock = stock;
        this.orderId = orderId;
        this.quantity = quantity;
    }
}
