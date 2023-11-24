package com.dskora.quarkus.ecommerce.inventory.service;

import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreditLimitExceededEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.ProductOutOfStockEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.inventory.domain.Inventory;
import com.dskora.quarkus.ecommerce.inventory.domain.ProductOutOfStockException;
import com.dskora.quarkus.ecommerce.inventory.dto.RegisterProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.repository.InventoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Arrays;

@ApplicationScoped
public class InventoryService {
    @Inject
    InventoryRepository inventoryRepository;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Inventory registerProductStock(RegisterProductStockRequest inventoryRequest) {
        ResultWithEvents<Inventory> inventoryWithEvents = Inventory.create(inventoryRequest.getProductId(), inventoryRequest.getStock());
        Inventory inventory = inventoryWithEvents.result;

        inventoryRepository.persist(inventory);
        domainEventPublisher.publish(Inventory.class, inventory.getId(), inventoryWithEvents.events);

        return inventory;
    }

    @Transactional
    public void reserveStock(OrderCreatedEvent event)
    {
        Inventory inventory = inventoryRepository.findByProductId(event.getProductId());

        try {
            ResultWithEvents<Inventory> inventoryWithEvents = inventory.reserveStock(event.getOrderId(), event.getCustomerId(), event.getQuantity());
            inventoryRepository.persist(inventory);
            domainEventPublisher.publish(Inventory.class, inventory.getId(), inventoryWithEvents.events);
        } catch (ProductOutOfStockException e) {
            domainEventPublisher.publish(Inventory.class, inventory.getId(), Arrays.asList(new ProductOutOfStockEvent(event.getOrderId(), event.getCustomerId(), inventory.getStock())));
        }
    }
}
