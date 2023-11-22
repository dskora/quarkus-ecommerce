package com.dskora.quarkus.ecommerce.customer.service;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.customer.domain.Customer;
import com.dskora.quarkus.ecommerce.customer.dto.CustomerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CustomerService {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    DomainEventPublisher domainEventPublisher;

    @Transactional
    public Customer createCustomer(CustomerRequest customerRequest) {
        ResultWithEvents<Customer> customerWithEvents = Customer.create(customerRequest.getName());
        Customer customer = customerWithEvents.result;

        entityManager.persist(customer);
        domainEventPublisher.publish(Customer.class.getSimpleName().toLowerCase(), customer.getId(), customerWithEvents.events);

        return customer;
    }
}
