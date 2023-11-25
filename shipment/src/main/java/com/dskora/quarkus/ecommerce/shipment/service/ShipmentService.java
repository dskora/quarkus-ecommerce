package com.dskora.quarkus.ecommerce.shipment.service;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import com.dskora.quarkus.ecommerce.shipment.domain.Shipment;
import com.dskora.quarkus.ecommerce.shipment.dto.CreateShipmentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class ShipmentService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Shipment requestShipment(UUID orderId, ShipmentProvider provider) {
        ResultWithEvents<Shipment> shipmentWithEvents = Shipment.request(orderId, provider);
        Shipment shipment = shipmentWithEvents.result;

        entityManager.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), shipmentWithEvents.events);

        return shipment;
    }

    @Transactional
    public Shipment completeShipment(UUID id) {
        Shipment shipment = entityManager.find(Shipment.class, id);

        ResultWithEvents<Shipment> customerWithEvents = shipment.complete();
        entityManager.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), customerWithEvents.events);

        return shipment;
    }

    @Transactional
    public Shipment cancelShipment(UUID id) {
        Shipment shipment = entityManager.find(Shipment.class, id);

        ResultWithEvents<Shipment> customerWithEvents = shipment.cancel();
        entityManager.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), customerWithEvents.events);

        return shipment;
    }
}
