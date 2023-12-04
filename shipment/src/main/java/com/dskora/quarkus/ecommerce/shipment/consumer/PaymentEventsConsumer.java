package com.dskora.quarkus.ecommerce.shipment.consumer;

import com.dskora.quarkus.ecommerce.common.domain.api.PaymentCompletedEvent;
import com.dskora.quarkus.ecommerce.shipment.service.ShipmentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class PaymentEventsConsumer {
    @Inject
    ShipmentService shipmentService;

    @Incoming("payment.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String eventType = new String(message.getHeaders().lastHeader("type").value());
                if (eventType.equals(PaymentCompletedEvent.class.getSimpleName())) {
                    PaymentCompletedEvent paymentCompletedEvent = objectMapper.readValue(message.getPayload(), PaymentCompletedEvent.class);
                    shipmentService.prepareShipment(paymentCompletedEvent.getOrderId());
                }

                message.ack();
            } catch(JsonMappingException e) {
                message.nack(e);
            } catch(JsonProcessingException e) {
                message.nack(e);
            }
        });
    }
}