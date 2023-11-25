package com.dskora.quarkus.ecommerce.order.consumer;

import com.dskora.quarkus.ecommerce.common.domain.api.ShipmentDeliveredEvent;
import com.dskora.quarkus.ecommerce.order.service.OrderService;
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
public class ShipmentEventsConsumer {
    @Inject
    OrderService orderService;

    @Incoming("shipment.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String eventType = new String(message.getHeaders().lastHeader("type").value());
                if (eventType.equals(ShipmentDeliveredEvent.class.getSimpleName())) {
                    ShipmentDeliveredEvent shipmentDeliveredEvent = objectMapper.readValue(message.getPayload(), ShipmentDeliveredEvent.class);
                    orderService.completeOrder(shipmentDeliveredEvent.getOrderId());
                }

                message.ack();
            } catch(JsonMappingException e) {
                System.out.println(e.getMessage());
            } catch(JsonProcessingException e) {
                System.out.println(e.getMessage());
                message.nack(e);
            }
        });
    }
}