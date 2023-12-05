package com.dskora.quarkus.ecommerce.inventory.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class StockRepository implements PanacheRepository<Stock> {
    public Stock findByProductId(UUID productId){
        return find("productId", productId).firstResult();
    }
}
