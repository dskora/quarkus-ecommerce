package com.dskora.quarkus.ecommerce.common.domain.event.publisher;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;

import java.util.List;
import java.util.UUID;

public interface DomainEventPublisher {
    public void publish(String aggregateType, UUID aggregateId, List<DomainEvent> domainEvents);

    default public void publish(Class<?> aggregateType, UUID aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType.getSimpleName().toLowerCase(), aggregateId, domainEvents);
    }
}