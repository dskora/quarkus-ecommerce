package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditLimitExceededEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Customer createCustomer(String name, Money creditLimit) {
        ResultWithEvents<Customer> customerWithEvents = Customer.create(name, creditLimit);
        Customer customer = customerWithEvents.result;

        entityManager.persist(customer);
        domainEventPublisher.publish(Customer.class, customer.getId(), customerWithEvents.events);

        return customer;
    }

    @Transactional
    public Customer reserveCredit(UUID orderId, UUID customerId, Money amount)
    {
        Customer customer = entityManager.find(Customer.class, customerId);

        try {
            ResultWithEvents<Customer> customerWithEvents = customer.reserveCredit(orderId, amount);
            entityManager.persist(customer);
            domainEventPublisher.publish(Customer.class, customer.getId(), customerWithEvents.events);
        } catch (CustomerCreditLimitExceededException e) {
            domainEventPublisher.publish(Customer.class, customer.getId(), Arrays.asList(new CustomerCreditLimitExceededEvent(customerId, orderId, amount)));
        }

        return customer;
    }
}
