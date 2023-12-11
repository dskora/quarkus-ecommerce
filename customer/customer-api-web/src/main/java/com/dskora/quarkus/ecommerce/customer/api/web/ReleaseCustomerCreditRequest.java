package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseCustomerCreditRequest {
     private UUID orderId;
     private UUID customerId;
}