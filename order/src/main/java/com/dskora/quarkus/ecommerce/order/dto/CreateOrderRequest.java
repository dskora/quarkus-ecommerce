package com.dskora.quarkus.ecommerce.order.dto;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentDetails;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderRequest {
     private UUID customerId;

     private UUID productId;

     private BigDecimal total;

     private PaymentDetails paymentDetails;

     public CreateOrderRequest() {}

     public CreateOrderRequest(UUID customerId, UUID productId, BigDecimal total, PaymentDetails paymentDetails) {
          this.customerId = customerId;
          this.productId = productId;
          this.total = total;
          this.paymentDetails = paymentDetails;
     }
}