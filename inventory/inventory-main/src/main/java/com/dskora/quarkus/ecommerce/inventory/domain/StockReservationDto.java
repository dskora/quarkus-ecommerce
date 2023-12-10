package com.dskora.quarkus.ecommerce.inventory.domain;

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
    private int quantityReserved;
    private int quantityLeft;
}
