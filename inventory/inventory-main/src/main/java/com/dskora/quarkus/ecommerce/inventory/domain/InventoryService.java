package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.ProductOutOfStockEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.inventory.api.web.RegisterProductStockRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
