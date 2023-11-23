package com.dskora.quarkus.ecommerce.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegisterProductStockResponse {
    private UUID id;

    public RegisterProductStockResponse() {}

    public RegisterProductStockResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
