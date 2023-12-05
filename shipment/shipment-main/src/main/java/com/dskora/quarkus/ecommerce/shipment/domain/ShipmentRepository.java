package com.dskora.quarkus.ecommerce.shipment.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ShipmentRepository implements PanacheRepository<Shipment> {
    public Shipment findByOrderId(UUID orderId){
        return find("orderId", orderId).firstResult();
    }
}
