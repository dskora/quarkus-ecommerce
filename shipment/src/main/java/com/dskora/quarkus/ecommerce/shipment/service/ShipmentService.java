package com.dskora.quarkus.ecommerce.shipment.service;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.shipment.domain.Shipment;
import com.dskora.quarkus.ecommerce.shipment.dto.ShipmentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ShipmentService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Shipment createShipment(ShipmentRequest shipmentRequest) {
        ResultWithEvents<Shipment> shipmentWithEvents = Shipment.create(shipmentRequest.getName());
        Shipment shipment = shipmentWithEvents.result;

        entityManager.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), shipmentWithEvents.events);

        return shipment;
    }
}
