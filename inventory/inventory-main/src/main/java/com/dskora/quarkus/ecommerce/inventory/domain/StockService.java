package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import com.dskora.quarkus.ecommerce.inventory.event.ProductOutOfStockEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class StockService {
    @Inject
    StockRepository stockRepository;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Stock registerProductStock(UUID productId, StockQuantity quantity) {
        Optional<Stock> stock = stockRepository.findByProductIdOptional(productId);
        ResultWithEvents<Stock> stockWithEvents = stock.isPresent()
            ? stock.get().increaseStock(quantity)
            : Stock.create(productId, quantity)
        ;

        stockRepository.persist(stockWithEvents.result);
        domainEventPublisher.publish(Stock.class, stockWithEvents.result.getId(), stockWithEvents.events);

        return stockWithEvents.result;
    }

    @Transactional
    public StockReservationDto reserveProductStock(UUID orderId, UUID productId, StockQuantity quantity)
    {
        Stock stock = stockRepository.findByProductId(productId);

        try {
            ResultWithEvents<Stock> stockWithEvents = stock.reserveStock(orderId, quantity);
            stockRepository.persist(stockWithEvents.result);
            domainEventPublisher.publish(Stock.class, stock.getId(), stockWithEvents.events);
        } catch (ProductOutOfStockException e) {
            domainEventPublisher.publish(Stock.class, stock.getId(), Arrays.asList(new ProductOutOfStockEvent(orderId, productId, stock.getQuantity())));
        }

        return new StockReservationDto(orderId, productId, quantity, stock.getQuantity());
    }

    @Transactional
    public Stock releaseProductStock(UUID orderId, UUID productId)
    {
        Stock stock = stockRepository.findByProductId(productId);

        ResultWithEvents<Stock> stockWithEvents = stock.releaseStock(orderId);
        stockRepository.persist(stock);
        domainEventPublisher.publish(Stock.class, stock.getId(), stockWithEvents.events);

        return stock;
    }

    public StockDto findStock(UUID productId) {
        Stock stock = stockRepository.findByProductId(productId);
        return new StockDto(stock.getProductId(), stock.getQuantity());
    }
}
