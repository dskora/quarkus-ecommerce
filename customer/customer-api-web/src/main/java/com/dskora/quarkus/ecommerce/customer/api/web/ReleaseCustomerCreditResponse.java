package com.dskora.quarkus.ecommerce.customer.api.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseCustomerCreditResponse {
     private UUID customerId;
}