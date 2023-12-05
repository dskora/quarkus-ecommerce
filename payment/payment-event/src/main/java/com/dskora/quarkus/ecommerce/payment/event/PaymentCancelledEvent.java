package com.dskora.quarkus.ecommerce.payment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentCancelledEvent implements DomainEvent {
    private UUID orderId;

    public PaymentCancelledEvent() {}

    public PaymentCancelledEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
