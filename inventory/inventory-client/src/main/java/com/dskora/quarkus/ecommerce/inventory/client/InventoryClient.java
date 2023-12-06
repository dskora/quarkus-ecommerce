package com.dskora.quarkus.ecommerce.inventory.client;

import com.dskora.quarkus.ecommerce.inventory.api.web.ReleaseProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReleaseProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://inventory-service")
public interface InventoryClient {
    @POST
    @Path("/inventory/stock/reserve")
    ReserveProductStockResponse reserveProductStock(ReserveProductStockRequest reserveProductStockRequest);

    @POST
    @Path("/inventory/stock/release")
    ReleaseProductStockResponse releaseProductStock(ReleaseProductStockRequest releaseProductStockRequest);
}
