package com.dskora.quarkus.ecommerce.payment.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentState;

import java.util.UUID;

public class CreatePaymentRequest {
     private UUID orderId;

     private PaymentMethod paymentMethod;

     private PaymentState paymentState;

     public CreatePaymentRequest() {}

     public CreatePaymentRequest(UUID orderId, PaymentMethod paymentMethod, PaymentState paymentState) {
          this.orderId = orderId;
          this.paymentMethod = paymentMethod;
          this.paymentState = paymentState;
     }

     public UUID getOrderId() {
          return orderId;
     }

     public PaymentMethod getPaymentMethod() {
          return paymentMethod;
     }

     public PaymentState getPaymentState() {
          return paymentState;
     }
}