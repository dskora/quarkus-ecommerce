package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderCreatedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    private UUID productId;

    private Money total;

    private int quantity;

    private PaymentDetails paymentDetails;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, UUID customerId, Money total, UUID productId, int quantity, PaymentDetails paymentDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.quantity = quantity;
        this.productId = productId;
        this.paymentDetails = paymentDetails;
    }
}
