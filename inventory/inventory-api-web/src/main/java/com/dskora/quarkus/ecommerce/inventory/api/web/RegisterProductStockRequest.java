package com.dskora.quarkus.ecommerce.inventory.api.web;

import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterProductStockRequest {
     private UUID productId;

     private int quantity;
}