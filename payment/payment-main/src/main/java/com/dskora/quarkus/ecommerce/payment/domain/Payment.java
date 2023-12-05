package com.dskora.quarkus.ecommerce.payment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.common.PaymentMethod;
import com.dskora.quarkus.ecommerce.payment.common.PaymentState;
import com.dskora.quarkus.ecommerce.payment.event.PaymentCancelledEvent;
import com.dskora.quarkus.ecommerce.payment.event.PaymentCompletedEvent;
import com.dskora.quarkus.ecommerce.payment.event.PaymentRefundedEvent;
import com.dskora.quarkus.ecommerce.payment.event.PaymentRequestedEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "payments")
public class Payment {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID customerId;

    @Embedded
    private Money amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;

    protected Payment() {}

    protected Payment(UUID orderId, UUID customerId, Money amount, PaymentMethod paymentMethod) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.amount = amount;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.paymentState = PaymentState.REQUESTED;
    }

    public static ResultWithEvents<Payment> request(UUID orderId, UUID customerId, Money amount, PaymentMethod paymentMethod)
    {
        Payment payment = new Payment(orderId, customerId, amount, paymentMethod);
        PaymentRequestedEvent event = new PaymentRequestedEvent(orderId, customerId, amount, paymentMethod);

        return new ResultWithEvents<>(payment, event);
    }

    public ResultWithEvents<Payment> complete()
    {
        if (paymentState == PaymentState.COMPLETED || paymentState != PaymentState.REQUESTED) {
            return new ResultWithEvents<>(this);
        }

        this.paymentState = PaymentState.COMPLETED;
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Payment> cancel()
    {
        if (paymentState == PaymentState.CANCELLED || paymentState != PaymentState.REQUESTED) {
            return new ResultWithEvents<>(this);
        }

        this.paymentState = PaymentState.COMPLETED;
        PaymentCancelledEvent event = new PaymentCancelledEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Payment> refund()
    {
        if (paymentState == PaymentState.REFUNDED || paymentState == PaymentState.REQUESTED) {
            return new ResultWithEvents<>(this);
        }

        this.paymentState = PaymentState.COMPLETED;
        PaymentRefundedEvent event = new PaymentRefundedEvent(orderId);

        return new ResultWithEvents<>(this, event);
    }

    public UUID getId() {
        return id;
    }
}
