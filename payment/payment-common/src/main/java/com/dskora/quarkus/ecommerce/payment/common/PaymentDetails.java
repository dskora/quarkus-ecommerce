package com.dskora.quarkus.ecommerce.payment.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PaymentDetails {
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public PaymentDetails() {}

    public PaymentDetails(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonIgnore
    public boolean isCreditPayment() {
        return paymentMethod == PaymentMethod.ACCOUNT_CREDIT;
    }
}
