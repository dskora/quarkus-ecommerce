package com.dskora.quarkus.ecommerce.customer.event;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerCreditReservedEvent implements DomainEvent {
    private UUID orderId;
    private Money total;

    public CustomerCreditReservedEvent() {}

    public CustomerCreditReservedEvent(UUID orderId, Money total) {
        this.orderId = orderId;
        this.total = total ;
    }
}
