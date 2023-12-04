package com.dskora.quarkus.ecommerce.inventory.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class InventoryRepository  implements PanacheRepository<Inventory> {
    public Inventory findByProductId(UUID productId){
        return find("productId", productId).firstResult();
    }
}
