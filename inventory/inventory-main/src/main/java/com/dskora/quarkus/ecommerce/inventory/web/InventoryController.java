package com.dskora.quarkus.ecommerce.inventory.web;

import com.dskora.quarkus.ecommerce.inventory.api.web.*;
import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import com.dskora.quarkus.ecommerce.inventory.domain.Stock;
import com.dskora.quarkus.ecommerce.inventory.domain.StockDto;
import com.dskora.quarkus.ecommerce.inventory.domain.StockReservationDto;
import com.dskora.quarkus.ecommerce.inventory.domain.StockService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.UUID;

@Path("/inventory")
public class InventoryController {
    @Inject
    StockService stockService;

    @POST
    @Path("/stock")
    @ResponseStatus(201)
    public RegisterProductStockResponse registerStock(RegisterProductStockRequest request) {
        Stock stock = stockService.registerProductStock(request.getProductId(), new StockQuantity(request.getQuantity()));
        return new RegisterProductStockResponse(stock.getId());
    }

    @POST
    @Path("/stock/reserve")
    public ReserveProductStockResponse reserveStock(ReserveProductStockRequest request) {
        StockReservationDto stockReservationDto = stockService.reserveProductStock(request.getOrderId(), request.getProductId(), new StockQuantity(request.getQuantity()));
        return new ReserveProductStockResponse(stockReservationDto.getOrderId(), stockReservationDto.getProductId(), stockReservationDto.getQuantityReserved().getNumber(), stockReservationDto.getQuantityLeft().getNumber());
    }

    @POST
    @Path("/stock/release")
    public ReleaseProductStockResponse releaseStock(ReleaseProductStockRequest request) {
        Stock stock = stockService.releaseProductStock(request.getOrderId(), request.getProductId(), new StockQuantity(request.getQuantity()));
        return new ReleaseProductStockResponse(stock.getId());
    }

    @GET
    @Path("/stock/{id}")
    public StockResponse findStock(@PathParam("id") UUID id) {
        StockDto stockDto = this.stockService.findStock(id);
        return new StockResponse(stockDto.getProductId(), stockDto.getQuantity().getNumber());
    }
}