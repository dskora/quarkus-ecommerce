package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerResponse {
    private UUID id;
}
