package com.dskora.quarkus.ecommerce.inventory.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class StockQuantity {
    @Column(name = "quantity")
    private int quantity;

    public StockQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidStockQuantityException("Stock quantity must be higher than 0");
        }

        this.quantity = quantity;
    }

    public boolean isGreaterThan(StockQuantity other) {
        return quantity > other.getQuantity();
    }

    public StockQuantity add(StockQuantity other) {
        return new StockQuantity(quantity + other.getQuantity());
    }

    public StockQuantity subtract(StockQuantity other) {
        return new StockQuantity(quantity - other.getQuantity());
    }
}
