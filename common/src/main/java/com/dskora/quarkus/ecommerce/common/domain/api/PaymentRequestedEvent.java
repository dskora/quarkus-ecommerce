package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentState;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

@Getter
@Setter
public class PaymentRequestedEvent implements DomainEvent {
    private UUID orderId;

    private PaymentMethod paymentMethod;

    private PaymentState paymentState;

    public PaymentRequestedEvent() {}

    public PaymentRequestedEvent(UUID orderId, PaymentMethod paymentMethod, PaymentState paymentState) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentState = paymentState;
    }
}
