package com.dskora.quarkus.ecommerce.inventory.dto;

import java.util.UUID;

public class InventoryResponse {
    private String id;

    public InventoryResponse() {}

    public InventoryResponse(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
