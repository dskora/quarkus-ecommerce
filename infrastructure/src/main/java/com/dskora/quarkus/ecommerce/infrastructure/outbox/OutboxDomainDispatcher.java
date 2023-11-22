package com.dskora.quarkus.ecommerce.infrastructure.outbox;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.publisher.DomainEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public class OutboxDomainDispatcher implements DomainEventPublisher {
    EntityManager entityManager;

    ObjectMapper objectMapper;

    public OutboxDomainDispatcher(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void publish(String aggregateType, UUID aggregateId, List<DomainEvent> domainEvents) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        domainEvents.forEach(event -> {
            String json = "";
            try {
                json = objectMapper.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                // handle exception to stop transaction
            }

            entityManager.createNativeQuery("INSERT INTO events VALUES (?, ?, ?, ?, ?)")
                .setParameter(1, UUID.randomUUID().toString())
                .setParameter(2, aggregateType)
                .setParameter(3, aggregateId.toString())
                .setParameter(4, event.getClass().getSimpleName())
                .setParameter(5, json)
                .executeUpdate();
        });

    }
}
