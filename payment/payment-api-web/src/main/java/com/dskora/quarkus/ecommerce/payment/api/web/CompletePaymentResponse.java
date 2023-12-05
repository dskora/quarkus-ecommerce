package com.dskora.quarkus.ecommerce.payment.api.web;

import java.util.UUID;

public class CompletePaymentResponse {
    private UUID id;

    public CompletePaymentResponse() {}

    public CompletePaymentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
