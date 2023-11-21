package com.dskora.quarkus.ecommerce.infrastructure.outbox;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public class OutboxDomainDispatcher implements DomainEventPublisher {
    EntityManager entityManager;

    public OutboxDomainDispatcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        domainEvents.forEach(event -> {
            entityManager.createNativeQuery("INSERT INTO events VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, UUID.randomUUID().toString())
                .setParameter(2, aggregateType)
                .setParameter(3, aggregateId)
                .setParameter(4, event.getClass())
                .setParameter(5, "122")
                .setParameter(6, 0)
                .executeUpdate();
        });

    }
}
