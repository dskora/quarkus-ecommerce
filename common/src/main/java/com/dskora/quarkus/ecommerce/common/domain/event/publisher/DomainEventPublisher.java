package com.dskora.quarkus.ecommerce.common.domain.event.publisher;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    public void publish(String aggregateType, Object aggregateId, List<DomainEvent> domainEvents);

    default public void publish(Class<?> aggregateType, Object aggregateId, List<DomainEvent> domainEvents) {
        publish(aggregateType.getName(), aggregateId, domainEvents);
    }
}