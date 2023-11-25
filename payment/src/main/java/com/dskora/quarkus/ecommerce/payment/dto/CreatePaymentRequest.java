package com.dskora.quarkus.ecommerce.payment.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentMethod;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentState;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentProvider;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreatePaymentRequest {
     private UUID orderId;

     private UUID customerId;

     private BigDecimal amount;

     private PaymentMethod paymentMethod;

     private ShipmentProvider orderShipmentProvider;

     public CreatePaymentRequest() {}

     public CreatePaymentRequest(UUID orderId, UUID customerId, BigDecimal amount, PaymentMethod paymentMethod, ShipmentProvider orderShipmentProvider) {
          this.orderId = orderId;
          this.customerId = customerId;
          this.amount = amount;
          this.paymentMethod = paymentMethod;
          this.orderShipmentProvider = orderShipmentProvider;
     }
}