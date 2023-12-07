package com.dskora.quarkus.ecommerce.customer.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomerDto {
    private UUID id;
    private String name;
    private BigDecimal creditLimit;
}
