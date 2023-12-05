package com.dskora.quarkus.ecommerce.payment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.common.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequestedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    private Money amount;

    private PaymentMethod paymentMethod;

    public PaymentRequestedEvent() {}

    public PaymentRequestedEvent(UUID orderId, UUID customerId, Money amount, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
}
