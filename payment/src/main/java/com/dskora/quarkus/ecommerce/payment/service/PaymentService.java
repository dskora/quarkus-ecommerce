package com.dskora.quarkus.ecommerce.payment.service;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.dto.PaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PaymentService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Payment createCustomer(CustomerRequest paymentRequest) {
        ResultWithEvents<Payment> paymentWithEvents = Customer.create(paymentRequest.getName());
        Payment payment = paymentWithEvents.result;

        entityManager.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), paymentWithEvents.events);

        return payment;
    }
}
