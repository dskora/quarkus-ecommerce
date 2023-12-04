package com.dskora.quarkus.ecommerce.order.saga;


import com.dskora.quarkus.ecommerce.common.domain.api.OrderCreatedEvent;
import com.dskora.quarkus.ecommerce.order.domain.OrderService;
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
public class OrderKafkaSaga {
    @Inject
    OrderService orderService;

    @Incoming("order.events")
    @Incoming("customer.events")
    @Incoming("shipment.events")
    @Incoming("inventory.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> onOrderMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String eventType = new String(message.getHeaders().lastHeader("type").value());
                if (eventType.equals(OrderCreatedEvent.class.getSimpleName())) {
                    OrderCreatedEvent event = objectMapper.readValue(message.getPayload(), OrderCreatedEvent.class);
                }

                message.ack();
            } catch (JsonMappingException e) {
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
                message.nack(e);
            }
        });
    }
}