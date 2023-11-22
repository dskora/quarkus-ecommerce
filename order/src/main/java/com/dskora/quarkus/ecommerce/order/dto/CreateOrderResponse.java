package com.dskora.quarkus.ecommerce.order.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderResponse {
    private final String id;
}
