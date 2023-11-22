package com.dskora.quarkus.ecommerce.shipment.controller;

import com.dskora.quarkus.ecommerce.shipment.domain.Shipment;
import com.dskora.quarkus.ecommerce.shipment.dto.ShipmentRequest;
import com.dskora.quarkus.ecommerce.shipment.dto.ShipmentResponse;
import com.dskora.quarkus.ecommerce.shipment.service.ShipmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/shipments")
public class ShipmentController {
    @Inject
    ShipmentService shipmentService;

    @POST
    public ShipmentResponse registerShipment(CustomerRequest shipmentRequest) {
        Shipment shipment = shipmentService.createShipment(shipmentRequest);
        return new ShipmentResponse(shipment.getId());
    }
}