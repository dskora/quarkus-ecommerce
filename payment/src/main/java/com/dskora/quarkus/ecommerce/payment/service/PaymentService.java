package com.dskora.quarkus.ecommerce.payment.service;

import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.dto.CreatePaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class PaymentService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Payment requestPayment(OrderCreatedEvent orderCreatedEvent) {
        ResultWithEvents<Payment> paymentWithEvents = Payment.request(orderCreatedEvent.getOrderId(), orderCreatedEvent.getCustomerId(), orderCreatedEvent.getTotal(), orderCreatedEvent.getPaymentDetails().getPaymentMethod());
        Payment payment = paymentWithEvents.result;

        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), paymentWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment requestPayment(CreatePaymentRequest paymentRequest) {
        ResultWithEvents<Payment> paymentWithEvents = Payment.request(paymentRequest.getOrderId(), paymentRequest.getCustomerId(), new Money(paymentRequest.getAmount()), paymentRequest.getPaymentMethod());
        Payment payment = paymentWithEvents.result;

        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), paymentWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment completePayment(UUID id) {
        Payment payment = entityManager.find(Payment.class, id);

        ResultWithEvents<Payment> customerWithEvents = payment.complete();
        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment cancelPayment(UUID id) {
        Payment payment = entityManager.find(Payment.class, id);

        ResultWithEvents<Payment> customerWithEvents = payment.cancel();
        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment refundPayment(UUID id) {
        Payment payment = entityManager.find(Payment.class, id);

        ResultWithEvents<Payment> customerWithEvents = payment.refund();
        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }
}
