package com.dskora.quarkus.ecommerce.order.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.OrderState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

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

    @Enumerated(EnumType.STRING)
    private OrderState state;

    protected Order() {}

    protected Order(UUID customerId, UUID productId, Money total) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.productId = productId;
        this.total = total;
        this.state = OrderState.REQUESTED;
    }

    public static ResultWithEvents<Order> create(UUID customerId, UUID productId, Money total)
    {
        Order order = new Order(customerId, productId, total);
        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), customerId, total);

        return new ResultWithEvents<>(order, event);
    }

    public void reject() {
        this.state = OrderState.REJECTED;
    }

    public UUID getId() {
        return id;
    }
}
