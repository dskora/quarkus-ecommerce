package com.dskora.quarkus.ecommerce.customer;

import com.dskora.quarkus.ecommerce.common.domain.event.DomainEvent;
import com.dskora.quarkus.ecommerce.customer.api.CustomerCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.Header;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class Kafka {
    @Incoming("customer.events")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            System.out.println(message.getKey());
            String eventId = getHeaderAsString(message, "id");
            String type = getHeaderAsString(message, "type");

            ObjectMapper mapper = new ObjectMapper();
            try {
                Class eventClass = Class.forName("com.dskora.quarkus.ecommerce.customer.api." + type);
                DomainEvent event = mapper.readValue(message.getPayload(), DomainEvent.class);
            } catch (Exception e) {
                System.out.println("Ds");
            }


            System.out.println(type);
            System.out.println(eventId);

            message.ack();
        });
    }

    private String getHeaderAsString(KafkaRecord<?, ?> record, String name) {
        Header header = record.getHeaders().lastHeader(name);
        if (header == null) {
            throw new IllegalArgumentException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), Charset.forName("UTF-8"));
    }
}