package com.dskora.quarkus.ecommerce.customer.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;

public class CustomerCreatedEvent implements DomainEvent {
    private String name;

    public CustomerCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
