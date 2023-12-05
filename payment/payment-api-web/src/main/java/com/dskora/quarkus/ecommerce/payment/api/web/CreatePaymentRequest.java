package com.dskora.quarkus.ecommerce.payment.api.web;

import com.dskora.quarkus.ecommerce.payment.common.PaymentMethod;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreatePaymentRequest {
     private UUID orderId;

     private UUID customerId;

     private BigDecimal amount;

     private PaymentMethod paymentMethod;

     public CreatePaymentRequest() {}

     public CreatePaymentRequest(UUID orderId, UUID customerId, BigDecimal amount, PaymentMethod paymentMethod) {
          this.orderId = orderId;
          this.customerId = customerId;
          this.amount = amount;
          this.paymentMethod = paymentMethod;
     }
}