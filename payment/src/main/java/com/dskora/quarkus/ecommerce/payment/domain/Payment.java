package com.dskora.quarkus.ecommerce.payment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentState;
import com.dskora.quarkus.ecommerce.payment.api.PaymentCreatedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Arrays;
import java.util.UUID;

@Entity(name = "payments")
public class Payment {
    @Id
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    private PaymentMethod paymentMethod;

    private PaymentState paymentState;

    protected Payment() {}

    protected Payment(UUID orderId, PaymentMethod paymentMethod, PaymentState paymentState) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentState = paymentState;
    }

    public static ResultWithEvents<Payment> request(UUID orderId, PaymentMethod paymentMethod, PaymentState paymentState)
    {
        Payment payment = new Payment(orderId, paymentMethod, paymentState);
        PaymentCreatedEvent event = new PaymentCreatedEvent(orderId, paymentMethod, paymentState);

        return new ResultWithEvents<>(payment, event);
    }

    public UUID getId() {
        return id;
    }
}
