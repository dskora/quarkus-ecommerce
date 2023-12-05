package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.inventory.event.ProductOutOfStockEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.UUID;

@ApplicationScoped
public class StockService {
    @Inject
    StockRepository inventoryRepository;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Stock registerProductStock(UUID productId, int quantity) {
        ResultWithEvents<Stock> inventoryWithEvents = Stock.create(productId, quantity);
        Stock inventory = inventoryWithEvents.result;

        inventoryRepository.persist(inventory);
        domainEventPublisher.publish(Stock.class, inventory.getId(), inventoryWithEvents.events);

        return inventory;
    }

    @Transactional
    public Stock reserveProductStock(UUID orderId, UUID productId, int quantity)
    {
        Stock inventory = inventoryRepository.findByProductId(productId);

        try {
            ResultWithEvents<Stock> inventoryWithEvents = inventory.reserveStock(orderId, quantity);
            inventoryRepository.persist(inventory);
            domainEventPublisher.publish(Stock.class, inventory.getId(), inventoryWithEvents.events);
        } catch (ProductOutOfStockException e) {
            domainEventPublisher.publish(Stock.class, inventory.getId(), Arrays.asList(new ProductOutOfStockEvent(orderId, productId, inventory.getQuantity())));
        }

        return inventory;
    }
}
