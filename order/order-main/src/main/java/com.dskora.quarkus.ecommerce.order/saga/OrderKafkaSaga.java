package com.dskora.quarkus.ecommerce.order.saga;


import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditResponse;
import com.dskora.quarkus.ecommerce.customer.client.CustomerClient;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditLimitExceededEvent;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditReservedEvent;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReleaseProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReleaseProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockRequest;
import com.dskora.quarkus.ecommerce.inventory.api.web.ReserveProductStockResponse;
import com.dskora.quarkus.ecommerce.inventory.client.InventoryClient;
import com.dskora.quarkus.ecommerce.inventory.event.ProductOutOfStockEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReleasedEvent;
import com.dskora.quarkus.ecommerce.inventory.event.ProductStockReservedEvent;
import com.dskora.quarkus.ecommerce.order.domain.Order;
import com.dskora.quarkus.ecommerce.order.domain.OrderService;
import com.dskora.quarkus.ecommerce.payment.api.web.CancelPaymentResponse;
import com.dskora.quarkus.ecommerce.payment.api.web.CompletePaymentResponse;
import com.dskora.quarkus.ecommerce.payment.api.web.CreatePaymentRequest;
import com.dskora.quarkus.ecommerce.payment.api.web.CreatePaymentResponse;
import com.dskora.quarkus.ecommerce.payment.client.PaymentClient;
import com.dskora.quarkus.ecommerce.payment.common.PaymentMethod;
import com.dskora.quarkus.ecommerce.payment.event.PaymentCancelledEvent;
import com.dskora.quarkus.ecommerce.payment.event.PaymentCompletedEvent;
import com.dskora.quarkus.ecommerce.payment.event.PaymentRequestedEvent;
import com.dskora.quarkus.ecommerce.shipment.ShipmentClient;
import com.dskora.quarkus.ecommerce.shipment.api.web.CreateShipmentRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderKafkaSaga {
    @Inject
    OrderService orderService;

    @Inject
    @RestClient
    InventoryClient inventoryClient;

    @Inject
    @RestClient
    PaymentClient paymentClient;

    @Inject
    @RestClient
    CustomerClient customerClient;

    @Inject
    @RestClient
    ShipmentClient shipmentClient;

    @Incoming("order.events")
    @Incoming("customer.events")
    @Incoming("shipment.events")
    @Incoming("stock.events")
    @Incoming("payment.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    @Transactional
    public CompletionStage<Void> onOrderMessage(KafkaRecord<String, String> message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String eventType = new String(message.getHeaders().lastHeader("type").value());

            /* Inventory Events */
            if (eventType.equals(OrderCreatedEvent.class.getSimpleName())) {
                OrderCreatedEvent event = objectMapper.readValue(message.getPayload(), OrderCreatedEvent.class);
                ReserveProductStockResponse response = this.inventoryClient.reserveProductStock(new ReserveProductStockRequest(event.getOrderId(), event.getProductId(), event.getQuantity()));
            }
            if (eventType.equals(ProductStockReservedEvent.class.getSimpleName())) {
                ProductStockReservedEvent event = objectMapper.readValue(message.getPayload(), ProductStockReservedEvent.class);
                Order order = this.orderService.findOrder(event.getOrderId());
                CreatePaymentResponse response = this.paymentClient.requestPayment(new CreatePaymentRequest(event.getOrderId(), order.getCustomerId(), order.getTotal().getAmount(), order.getPaymentDetails().getPaymentMethod()));
            }
            if (eventType.equals(ProductOutOfStockEvent.class.getSimpleName())) {
                ProductOutOfStockEvent event = objectMapper.readValue(message.getPayload(), ProductOutOfStockEvent.class);
                this.orderService.rejectOrder(event.getOrderId(), "Product out of stock");
            }
            if (eventType.equals(PaymentRequestedEvent.class.getSimpleName())) {
                PaymentRequestedEvent event = objectMapper.readValue(message.getPayload(), PaymentRequestedEvent.class);
                if (event.getPaymentMethod() == PaymentMethod.ACCOUNT_CREDIT) {
                    ReserveCustomerCreditResponse response = this.customerClient.reserveCredit(new ReserveCustomerCreditRequest(event.getOrderId(), event.getCustomerId(), event.getAmount().getAmount()));
                }
            }
            if (eventType.equals(CustomerCreditReservedEvent.class.getSimpleName())) {
                CustomerCreditReservedEvent event = objectMapper.readValue(message.getPayload(), CustomerCreditReservedEvent.class);
                CreatePaymentResponse response = this.paymentClient.completePayment(event.getOrderId());
            }
            if (eventType.equals(PaymentCompletedEvent.class.getSimpleName())) {
                PaymentCompletedEvent event = objectMapper.readValue(message.getPayload(), PaymentCompletedEvent.class);
                Order order = this.orderService.findOrder(event.getOrderId());
                CreateShipmentRequest response = this.shipmentClient.requestShipment(new CreateShipmentRequest(event.getOrderId(), order.getShipmentDetails().getShipmentProvider(), order.getShipmentDetails().getShipmentAddress()));
            }
            if (eventType.equals(CustomerCreditLimitExceededEvent.class.getSimpleName())) {
                CustomerCreditLimitExceededEvent event = objectMapper.readValue(message.getPayload(), CustomerCreditLimitExceededEvent.class);
                CancelPaymentResponse response = this.paymentClient.cancelPayment(event.getOrderId());
            }
            if (eventType.equals(PaymentCancelledEvent.class.getSimpleName())) {
                PaymentCancelledEvent event = objectMapper.readValue(message.getPayload(), PaymentCancelledEvent.class);
                Order order = this.orderService.findOrder(event.getOrderId());
                ReleaseProductStockResponse response = this.inventoryClient.releaseProductStock(new ReleaseProductStockRequest(event.getOrderId(), order.getProductId()));
            }
            if (eventType.equals(ProductStockReleasedEvent.class.getSimpleName())) {
                ProductStockReleasedEvent event = objectMapper.readValue(message.getPayload(), ProductStockReleasedEvent.class);
                this.orderService.rejectOrder(event.getOrderId(), "");
            }
        } catch (JsonMappingException e) {
            return message.nack(e);
        } catch (Exception e) {
            return message.nack(e);
        }

        return message.ack();
    }
}