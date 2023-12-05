package com.dskora.quarkus.ecommerce.customer.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.UUID;

@Getter
@Setter
public class CustomerCreditLimitExceededEvent implements DomainEvent {
    private UUID customerId;

    private UUID orderId;

    private Money creditLimit;

    public CustomerCreditLimitExceededEvent() {}

    public CustomerCreditLimitExceededEvent(UUID customerId, UUID orderId, Money creditLimit) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.creditLimit = creditLimit;
    }
}
