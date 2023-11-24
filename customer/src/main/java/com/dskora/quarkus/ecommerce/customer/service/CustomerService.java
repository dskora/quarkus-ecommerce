package com.dskora.quarkus.ecommerce.customer.service;

import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreditLimitExceededEvent;
import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.domain.Customer;
import com.dskora.quarkus.ecommerce.customer.domain.CustomerCreditLimitExceededException;
import com.dskora.quarkus.ecommerce.customer.dto.CreateCustomerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Arrays;

@ApplicationScoped
public class CustomerService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Customer createCustomer(CreateCustomerRequest customerRequest) {
        ResultWithEvents<Customer> customerWithEvents = Customer.create(customerRequest.getName(), new Money(customerRequest.getCreditLimit()));
        Customer customer = customerWithEvents.result;

        entityManager.persist(customer);
        domainEventPublisher.publish(Customer.class, customer.getId(), customerWithEvents.events);

        return customer;
    }

    @Transactional
    public void reserveCredit(OrderCreatedEvent event)
    {
        Customer customer = entityManager.find(Customer.class, event.getCustomerId());

        try {
            ResultWithEvents<Customer> customerWithEvents = customer.reserveCredit(event.getTotal());
            entityManager.persist(customer);
            domainEventPublisher.publish(Customer.class, customer.getId(), customerWithEvents.events);
        } catch (CustomerCreditLimitExceededException e) {
            domainEventPublisher.publish(Customer.class, customer.getId(), Arrays.asList(new CustomerCreditLimitExceededEvent(event.getCustomerId(), event.getOrderId(), customer.getCreditLimit())));
        }
    }
}
