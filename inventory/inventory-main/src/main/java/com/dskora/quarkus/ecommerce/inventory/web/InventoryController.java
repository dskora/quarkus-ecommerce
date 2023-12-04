package com.dskora.quarkus.ecommerce.inventory.web;

import com.dskora.quarkus.ecommerce.inventory.api.web.RegisterProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.RegisterProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.domain.Inventory;
import com.dskora.quarkus.ecommerce.inventory.domain.InventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/inventory")
public class InventoryController {
    @Inject
    InventoryService inventoryService;

    @POST
    @Path("/products")
    public RegisterProductStockResponse registerProduct(RegisterProductStockRequest inventoryRequest) {
        Inventory inventory = inventoryService.registerProductStock(inventoryRequest);
        return new RegisterProductStockResponse(inventory.getId());
    }
}