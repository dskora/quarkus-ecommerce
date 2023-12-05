package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.common.PaymentDetails;
import com.dskora.quarkus.ecommerce.shipment.common.ShipmentDetails;
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

    private ShipmentDetails shipmentDetails;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, UUID customerId, Money total, UUID productId, int quantity, PaymentDetails paymentDetails, ShipmentDetails shipmentDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.total = total;
        this.quantity = quantity;
        this.productId = productId;
        this.paymentDetails = paymentDetails;
        this.shipmentDetails = shipmentDetails;
    }
}
