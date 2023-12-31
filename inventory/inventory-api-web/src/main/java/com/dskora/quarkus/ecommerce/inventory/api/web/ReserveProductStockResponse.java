package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductStockResponse {
    private UUID orderId;
    private UUID productId;
    private int quantityReserved;
    private int quantityLeft;
}
