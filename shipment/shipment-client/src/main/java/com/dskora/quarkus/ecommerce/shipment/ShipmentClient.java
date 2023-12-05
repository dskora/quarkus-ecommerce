package com.dskora.quarkus.ecommerce.shipment;

import com.dskora.quarkus.ecommerce.shipment.api.web.CreateShipmentRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://shipment-service")
public interface ShipmentClient {
    @POST
    @Path("/shipments")
    CreateShipmentRequest requestShipment(CreateShipmentRequest shipmentRequest);
}