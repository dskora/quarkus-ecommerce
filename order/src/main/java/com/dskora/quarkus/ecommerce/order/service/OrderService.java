package com.dskora.quarkus.ecommerce.order.service;

import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreditLimitExceededEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.order.domain.Order;
import com.dskora.quarkus.ecommerce.order.dto.CreateOrderRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class OrderService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Order createOrder(CreateOrderRequest orderRequest) {
        ResultWithEvents<Order> orderWithEvents = Order.create(orderRequest.getCustomerId(), orderRequest.getProductId(), new Money(orderRequest.getTotal()), orderRequest.getPaymentDetails());
        Order order = orderWithEvents.result;

        entityManager.persist(order);
        domainEventPublisher.publish(Order.class, order.getId(), orderWithEvents.events);

        return order;
    }

    @Transactional
    public void rejectOrder(CustomerCreditLimitExceededEvent event) {
        Order order = entityManager.find(Order.class, event.getOrderId());
        ResultWithEvents<Order> orderWithEvents = order.reject(String.format("Customer credit limit exceeded: [creditLimit = %s]", event.getCreditLimit().getAmount().toString()));

        entityManager.persist(order);
        domainEventPublisher.publish(Order.class, order.getId(), orderWithEvents.events);
    }
}
