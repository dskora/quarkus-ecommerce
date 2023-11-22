package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import lombok.Value;

import java.util.UUID;

@Value
public class CustomerCreditLimitExceededEvent implements DomainEvent {
    private UUID customerId;

    private UUID orderId;
}
