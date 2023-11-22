package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.InventoryCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "inventory")
public class Inventory {
    @Id
    private String id;

    private String name;

    protected Inventory() {}

    protected Inventory(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static ResultWithEvents<Inventory> create(String name)
    {
        Inventory inventory = new Inventory(name);
        InventoryCreatedEvent event = new InventoryCreatedEvent();

        return new ResultWithEvents<>(inventory, event);
    }

    public String getId() {
        return id;
    }
}
