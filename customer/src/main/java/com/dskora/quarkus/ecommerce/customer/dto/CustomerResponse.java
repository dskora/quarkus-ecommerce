package com.dskora.quarkus.ecommerce.customer.dto;

import java.util.UUID;

public class CustomerResponse {
    private String id;

    public CustomerResponse() {}

    public CustomerResponse(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
