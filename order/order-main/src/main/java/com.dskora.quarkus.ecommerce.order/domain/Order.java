package com.dskora.quarkus.ecommerce.order.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.OrderCompletedEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.OrderRejectedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.OrderState;
import com.dskora.quarkus.ecommerce.payment.common.PaymentDetails;
import com.dskora.quarkus.ecommerce.shipment.common.ShipmentDetails;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity(name = "orders")
public class Order {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID productId;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID customerId;

    @Embedded
    private Money total;

    private int quantity;

    @Embedded
    PaymentDetails paymentDetails;

    @Embedded
    ShipmentDetails shipmentDetails;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private String rejectionReason;

    protected Order() {}

    protected Order(UUID customerId, UUID productId, Money total, int quantity, PaymentDetails paymentDetails, ShipmentDetails shipmentDetails) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.productId = productId;
        this.total = total;
        this.quantity = quantity;
        this.paymentDetails = paymentDetails;
        this.shipmentDetails = shipmentDetails;
        this.state = OrderState.REQUESTED;
    }

    public static ResultWithEvents<Order> create(UUID customerId, UUID productId, Money total, int quantity, PaymentDetails paymentDetails, ShipmentDetails shipmentDetails)
    {
        Order order = new Order(customerId, productId, total, quantity, paymentDetails, shipmentDetails);
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), customerId, total, productId, quantity, paymentDetails, shipmentDetails);

        return new ResultWithEvents<>(order, event);
    }

    public ResultWithEvents<Order> reject(String rejectionReason) {
        this.state = OrderState.REJECTED;
        this.rejectionReason = rejectionReason;

        OrderRejectedEvent event = new OrderRejectedEvent(this.getId(), this.customerId);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Order> complete()
    {
        this.state = OrderState.COMPLETED;

        OrderCompletedEvent event = new OrderCompletedEvent(this.getId());

        return new ResultWithEvents<>(this, event);
    }

    public UUID getId() {
        return id;
    }
}
