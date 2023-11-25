package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentRefundedEvent implements DomainEvent {
    private UUID orderId;

    private UUID customerId;

    private Money amount;

    private PaymentMethod paymentMethod;

    private ShipmentProvider orderShipmentProvider;

    public PaymentRefundedEvent() {}

    public PaymentRefundedEvent(UUID orderId, UUID customerId, Money amount, PaymentMethod paymentMethod, ShipmentProvider orderShipmentProvider) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.orderShipmentProvider = orderShipmentProvider;
    }
}
