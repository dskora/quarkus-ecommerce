package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreatedEvent implements DomainEvent {
    private String name;

    private Money creditLimit;

    public CustomerCreatedEvent() {}

    public CustomerCreatedEvent(String name, Money creditLimit) {
        this.name = name;
        this.creditLimit = creditLimit;
    }
}
