package com.dskora.quarkus.ecommerce.payment.api.web;

import java.util.UUID;

public class CancelPaymentResponse {
    private UUID id;

    public CancelPaymentResponse() {}

    public CancelPaymentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
