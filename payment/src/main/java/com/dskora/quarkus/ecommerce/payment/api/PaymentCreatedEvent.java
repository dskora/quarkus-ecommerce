package com.dskora.quarkus.ecommerce.payment.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;

public class PaymentCreatedEvent implements DomainEvent {
    private String name;

    public PaymentCreatedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
