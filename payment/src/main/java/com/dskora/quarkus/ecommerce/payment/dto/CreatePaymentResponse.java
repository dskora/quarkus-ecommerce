package com.dskora.quarkus.ecommerce.payment.dto;

import java.util.UUID;

public class CreatePaymentResponse {
    private UUID id;

    public CreatePaymentResponse() {}

    public CreatePaymentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
