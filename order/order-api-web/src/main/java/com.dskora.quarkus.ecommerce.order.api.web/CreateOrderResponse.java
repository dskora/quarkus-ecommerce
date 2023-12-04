package com.dskora.quarkus.ecommerce.order.api.web;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderResponse {
    private final UUID id;
}
