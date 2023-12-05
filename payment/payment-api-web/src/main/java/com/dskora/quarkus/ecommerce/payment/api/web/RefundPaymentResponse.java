package com.dskora.quarkus.ecommerce.payment.api.web;

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
