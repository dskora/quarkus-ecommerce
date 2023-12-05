package com.dskora.quarkus.ecommerce.shipment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.shipment.common.ShipmentProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class ShipmentService {
    @Inject
    ShipmentRepository shipmentRepository;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Shipment requestShipment(UUID orderId, ShipmentProvider provider, String address) {
        ResultWithEvents<Shipment> shipmentWithEvents = Shipment.request(orderId, provider, address);
        Shipment shipment = shipmentWithEvents.result;

        shipmentRepository.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), shipmentWithEvents.events);

        return shipment;
    }

    @Transactional
    public Shipment completeShipment(UUID orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId);

        ResultWithEvents<Shipment> customerWithEvents = shipment.complete();
        shipmentRepository.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), customerWithEvents.events);

        return shipment;
    }

    @Transactional
    public Shipment cancelShipment(UUID orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId);

        ResultWithEvents<Shipment> customerWithEvents = shipment.cancel();
        shipmentRepository.persist(shipment);
        domainEventPublisher.publish(Shipment.class, shipment.getId(), customerWithEvents.events);

        return shipment;
    }
}
