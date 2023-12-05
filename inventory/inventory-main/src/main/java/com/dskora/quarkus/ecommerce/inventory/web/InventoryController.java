package com.dskora.quarkus.ecommerce.inventory.web;

import com.dskora.quarkus.ecommerce.inventory.api.web.RegisterProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.RegisterProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.domain.Stock;
import com.dskora.quarkus.ecommerce.inventory.domain.StockService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.UUID;

@Path("/inventory")
public class InventoryController {
    @Inject
    StockService inventoryService;

    @POST
    @Path("/stock")
    public RegisterProductStockResponse registerStock(RegisterProductStockRequest request) {
        Stock inventory = inventoryService.registerProductStock(request.getProductId(), request.getQuantity());
        return new RegisterProductStockResponse(inventory.getId());
    }

    @POST
    @Path("/stock/reserve")
    public ReserveProductStockResponse reserveStock(ReserveProductStockRequest request) {
        Stock inventory = inventoryService.reserveProductStock(request.getOrderId(), request.getProductId(), request.getQuantity());
        return new ReserveProductStockResponse(inventory.getId());
    }
}