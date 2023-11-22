package com.dskora.quarkus.ecommerce.inventory.controller;

import com.dskora.quarkus.ecommerce.inventory.domain.Inventory;
import com.dskora.quarkus.ecommerce.inventory.dto.InventoryRequest;
import com.dskora.quarkus.ecommerce.inventory.dto.InventoryResponse;
import com.dskora.quarkus.ecommerce.inventory.service.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/inventorys")
public class InventoryController {
    @Inject
    InventoryService inventoryService;

    @POST
    public InventoryResponse registerInventory(CustomerRequest inventoryRequest) {
        Inventory inventory = inventoryService.createInventory(inventoryRequest);
        return new InventoryResponse(inventory.getId());
    }
}