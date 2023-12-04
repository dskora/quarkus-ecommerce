package com.dskora.quarkus.ecommerce.shipment.controller;

import com.dskora.quarkus.ecommerce.shipment.domain.Shipment;
import com.dskora.quarkus.ecommerce.shipment.dto.CancelShipmentResponse;
import com.dskora.quarkus.ecommerce.shipment.dto.CompleteShipmentResponse;
import com.dskora.quarkus.ecommerce.shipment.dto.CreateShipmentRequest;
import com.dskora.quarkus.ecommerce.shipment.dto.CreateShipmentResponse;
import com.dskora.quarkus.ecommerce.shipment.service.ShipmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.util.UUID;

@Path("/shipments")
public class ShipmentController {
    @Inject
    ShipmentService shipmentService;

    @POST
    public CreateShipmentResponse prepareShipment(CreateShipmentRequest shipmentRequest) {
        Shipment shipment = shipmentService.requestShipment(shipmentRequest.getOrderId(), shipmentRequest.getShipmentProvider(), shipmentRequest.getAddress());
        return new CreateShipmentResponse(shipment.getId());
    }

    @PUT
    @Path("/{id}/complete")
    public CompleteShipmentResponse completeShipment(UUID id) {
        Shipment shipment = shipmentService.completeShipment(id);
        return new CompleteShipmentResponse(shipment.getId());
    }

    @PUT
    @Path("/{id}/cancel")
    public CancelShipmentResponse cancelShipment(UUID id) {
        Shipment shipment = shipmentService.cancelShipment(id);
        return new CancelShipmentResponse(shipment.getId());
    }
}