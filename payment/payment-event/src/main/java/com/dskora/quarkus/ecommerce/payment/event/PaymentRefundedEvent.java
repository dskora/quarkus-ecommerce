package com.dskora.quarkus.ecommerce.payment.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRefundedEvent implements DomainEvent {
    private UUID orderId;

    public PaymentRefundedEvent() {}

    public PaymentRefundedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
