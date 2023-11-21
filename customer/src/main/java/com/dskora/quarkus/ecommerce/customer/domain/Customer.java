package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.customer.api.CustomerCreatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "customers")
public class Customer {
    @Id
    private String id;

    private String name;

    protected Customer() {}

    protected Customer(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static ResultWithEvents<Customer> create(String name)
    {
        Customer customer = new Customer(name);
        CustomerCreatedEvent event = new CustomerCreatedEvent();

        return new ResultWithEvents<>(customer, event);
    }

    public String getId() {
        return id;
    }
}
