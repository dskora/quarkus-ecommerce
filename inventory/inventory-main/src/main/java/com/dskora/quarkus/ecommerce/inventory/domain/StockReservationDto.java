package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockReservationDto {
    private UUID orderId;
    private UUID productId;
    private StockQuantity quantityReserved;
    private StockQuantity quantityLeft;
}
