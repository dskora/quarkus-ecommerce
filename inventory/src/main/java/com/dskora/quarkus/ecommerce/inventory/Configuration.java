package com.dskora.quarkus.ecommerce.inventory;

import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.dskora.quarkus.ecommerce.infrastructure.outbox.OutboxDomainDispatcher;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

@Singleton
public class Configuration {
    @Singleton
    public DomainEventPublisher domainEventPublisherImplementation(EntityManager entityManager) {
        return new OutboxDomainDispatcher(entityManager);
    }
}