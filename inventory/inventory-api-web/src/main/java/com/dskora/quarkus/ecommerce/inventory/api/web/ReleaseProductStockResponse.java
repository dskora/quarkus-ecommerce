package com.dskora.quarkus.ecommerce.inventory.api.web;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReleaseProductStockResponse {
    private UUID id;

    public ReleaseProductStockResponse() {}

    public ReleaseProductStockResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
