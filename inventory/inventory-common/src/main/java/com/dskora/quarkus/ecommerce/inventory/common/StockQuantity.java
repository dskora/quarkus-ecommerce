package com.dskora.quarkus.ecommerce.inventory.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class StockQuantity {
    private int number;

    public boolean isGreaterThan(StockQuantity other) {
        return number > other.getNumber();
    }

    public StockQuantity add(StockQuantity other) {
        return new StockQuantity(number + other.getNumber());
    }

    public StockQuantity subtract(StockQuantity other) {
        return new StockQuantity(number - other.getNumber());
    }
}
