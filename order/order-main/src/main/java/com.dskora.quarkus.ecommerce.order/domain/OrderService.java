package com.dskora.quarkus.ecommerce.order.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.order.api.web.CreateOrderRequest;
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
        ResultWithEvents<Order> orderWithEvents = Order.create(orderRequest.getCustomerId(), orderRequest.getProductId(), new Money(orderRequest.getTotal()), orderRequest.getQuantity(), orderRequest.getPaymentDetails(), orderRequest.getShipmentDetails());
        Order order = orderWithEvents.result;

        entityManager.persist(order);
        domainEventPublisher.publish(Order.class, order.getId(), orderWithEvents.events);

        return order;
    }

    @Transactional
    public void completeOrder(UUID id) {
        Order order = entityManager.find(Order.class, id);
        ResultWithEvents<Order> orderWithEvents = order.complete();

        entityManager.persist(order);
        domainEventPublisher.publish(Order.class, order.getId(), orderWithEvents.events);
    }

    @Transactional
    public void rejectOrder(UUID id, String rejectionReason) {
        Order order = entityManager.find(Order.class, id);
        ResultWithEvents<Order> orderWithEvents = order.reject(rejectionReason);

        entityManager.persist(order);
        domainEventPublisher.publish(Order.class, order.getId(), orderWithEvents.events);
    }

    public Order findOrder(UUID orderId) {
        return entityManager.find(Order.class, orderId);
    }
}
