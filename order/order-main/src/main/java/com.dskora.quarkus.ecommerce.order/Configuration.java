package com.dskora.quarkus.ecommerce.order;

import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.infrastructure.outbox.OutboxDomainDispatcher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class Configuration {
    @ApplicationScoped
    public DomainEventPublisher domainEventPublisherImplementation(EntityManager entityManager) {
        return new OutboxDomainDispatcher(entityManager);
    }
}