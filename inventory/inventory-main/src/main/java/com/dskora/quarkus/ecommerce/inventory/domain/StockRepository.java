package com.dskora.quarkus.ecommerce.inventory.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {
    public Stock findByProductId(UUID productId){
        return find("productId", productId).firstResult();
    }

    public Optional<Stock> findByProductIdOptional(UUID productId){
        return find("productId", productId).firstResultOptional();
    }
}
