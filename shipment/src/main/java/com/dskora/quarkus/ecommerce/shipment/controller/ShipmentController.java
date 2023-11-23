package com.dskora.quarkus.ecommerce.shipment.controller;

import com.dskora.quarkus.ecommerce.shipment.domain.Shipment;
import com.dskora.quarkus.ecommerce.shipment.dto.CreateShipmentRequest;
import com.dskora.quarkus.ecommerce.shipment.dto.CreateShipmentResponse;
import com.dskora.quarkus.ecommerce.shipment.service.ShipmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/shipments")
public class ShipmentController {
    @Inject
    ShipmentService shipmentService;

    @POST
    public CreateShipmentResponse requestShipment(CreateShipmentRequest shipmentRequest) {
        Shipment shipment = shipmentService.requestShipment(shipmentRequest);
        return new CreateShipmentResponse(shipment.getId());
    }
}