package com.dskora.quarkus.ecommerce.customer;

import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.infrastructure.outbox.OutboxDomainDispatcher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Singleton
public class Configuration {
    @Singleton
    public DomainEventPublisher domainEventPublisherImplementation(EntityManager entityManager) {
        return new OutboxDomainDispatcher(entityManager);
    }
}