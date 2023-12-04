package com.dskora.quarkus.ecommerce.payment.consumer;

import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventsConsumer {
    @Inject
    PaymentService paymentService;

    @Incoming("order.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String eventType = new String(message.getHeaders().lastHeader("type").value());
                if (eventType.equals(OrderCreatedEvent.class.getSimpleName())) {
                    OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(message.getPayload(), OrderCreatedEvent.class);
                    paymentService.requestPayment(orderCreatedEvent);
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