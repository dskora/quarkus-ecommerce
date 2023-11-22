package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Value;

@Value
public class ShipmentCreatedEvent implements DomainEvent {
}
