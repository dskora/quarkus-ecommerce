package com.dskora.quarkus.ecommerce.payment.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.common.PaymentMethod;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.UUID;

@ApplicationScoped
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Payment requestPayment(UUID orderId, UUID customerId, Money amount, PaymentMethod paymentMethod) {
        ResultWithEvents<Payment> paymentWithEvents = Payment.request(orderId, customerId, amount, paymentMethod);
        Payment payment = paymentWithEvents.result;

        paymentRepository.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), paymentWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment completePayment(UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);

        ResultWithEvents<Payment> customerWithEvents = payment.complete();
        paymentRepository.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment cancelPayment(UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);

        ResultWithEvents<Payment> customerWithEvents = payment.cancel();
        paymentRepository.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }

    @Transactional
    public Payment refundPayment(UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);

        ResultWithEvents<Payment> customerWithEvents = payment.refund();
        paymentRepository.persist(payment);
        domainEventPublisher.publish(Payment.class, payment.getId(), customerWithEvents.events);

        return payment;
    }
}
