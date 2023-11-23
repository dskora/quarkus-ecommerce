package com.dskora.quarkus.ecommerce.inventory.service;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.inventory.domain.Inventory;
import com.dskora.quarkus.ecommerce.inventory.dto.RegisterProductStockRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class InventoryService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Inventory registerProductStock(RegisterProductStockRequest inventoryRequest) {
        ResultWithEvents<Inventory> inventoryWithEvents = Inventory.create(inventoryRequest.getProductId(), inventoryRequest.getStocks());
        Inventory inventory = inventoryWithEvents.result;

        entityManager.persist(inventory);
        domainEventPublisher.publish(Inventory.class, inventory.getId(), inventoryWithEvents.events);

        return inventory;
    }
}
