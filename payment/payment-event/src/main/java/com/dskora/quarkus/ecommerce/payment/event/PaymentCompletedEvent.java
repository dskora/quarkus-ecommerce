package com.dskora.quarkus.ecommerce.payment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentCompletedEvent implements DomainEvent {
    private UUID orderId;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
