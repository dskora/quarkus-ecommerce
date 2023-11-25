package com.dskora.quarkus.ecommerce.payment.dto;

import java.util.UUID;

public class RefundPaymentResponse {
    private UUID id;

    public RefundPaymentResponse() {}

    public RefundPaymentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
