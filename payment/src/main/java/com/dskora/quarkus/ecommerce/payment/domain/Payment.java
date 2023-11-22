package com.dskora.quarkus.ecommerce.payment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.payment.api.PaymentCreatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "payments")
public class Payment {
    @Id
    private String id;

    private String name;

    protected Payment() {}

    protected Payment(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static ResultWithEvents<Payment> create(String name)
    {
        Payment payment = new Payment(name);
        PaymentCreatedEvent event = new PaymentCreatedEvent(name);

        return new ResultWithEvents<>(payment, event);
    }

    public String getId() {
        return id;
    }
}
