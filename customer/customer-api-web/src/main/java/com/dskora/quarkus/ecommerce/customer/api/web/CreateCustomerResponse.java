package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCustomerResponse {
    private final UUID id;
}
