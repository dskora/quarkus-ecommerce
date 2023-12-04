package com.dskora.quarkus.ecommerce.order.api.web;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.PaymentDetails;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.ShipmentDetails;
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

     private int quantity;

     private PaymentDetails paymentDetails;

     private ShipmentDetails shipmentDetails;

     public CreateOrderRequest() {}

     public CreateOrderRequest(UUID customerId, UUID productId, BigDecimal total, int quantity, PaymentDetails paymentDetails, ShipmentDetails shipmentDetails) {
          this.customerId = customerId;
          this.productId = productId;
          this.total = total;
          this.quantity = quantity;
          this.paymentDetails = paymentDetails;
          this.shipmentDetails = shipmentDetails;
     }
}