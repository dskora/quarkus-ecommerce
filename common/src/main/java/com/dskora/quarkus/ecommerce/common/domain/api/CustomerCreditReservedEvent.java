package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreditReservedEvent implements DomainEvent {
    private Money total;

    public CustomerCreditReservedEvent() {}

    public CustomerCreditReservedEvent(Money total) {
        this.total = total ;
    }
}
