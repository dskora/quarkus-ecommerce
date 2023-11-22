package com.dskora.quarkus.ecommerce.payment.dto;

import java.util.UUID;

public class PaymentResponse {
    private String id;

    public PaymentResponse() {}

    public PaymentResponse(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
