package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class InventoryCreatedEvent implements DomainEvent {
}
