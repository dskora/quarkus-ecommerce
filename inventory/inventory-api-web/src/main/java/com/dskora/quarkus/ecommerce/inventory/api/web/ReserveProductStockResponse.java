package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReserveProductStockResponse {
    private UUID id;

    public ReserveProductStockResponse() {}

    public ReserveProductStockResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
