package com.dskora.quarkus.ecommerce.common.domain.api;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentState;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentCompletedEvent implements DomainEvent {
    private UUID orderId;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(UUID orderId) {
        this.orderId = orderId;
    }
}
