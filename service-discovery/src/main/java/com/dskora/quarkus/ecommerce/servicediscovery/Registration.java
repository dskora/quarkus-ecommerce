package com.dskora.quarkus.ecommerce.servicediscovery;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.mutiny.core.Vertx;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class Registration {
    @ConfigProperty(name = "consul.host") String host;
    @ConfigProperty(name = "consul.port") int port;

    @ConfigProperty(name = "inventory-service.host") String inventoryHost;
    @ConfigProperty(name = "inventory-service.port") int inventoryPort;

    @ConfigProperty(name = "shipment-service.host") String shipmentHost;
    @ConfigProperty(name = "shipment-service.port") int shipmentPort;

    @ConfigProperty(name = "payment-service.host") String paymentHost;
    @ConfigProperty(name = "payment-service.port") int paymentPort;

    @ConfigProperty(name = "customer-service.host") String customerHost;
    @ConfigProperty(name = "customer-service.port") int customerPort;

    public void init(@Observes StartupEvent ev, Vertx vertx) {
        ConsulClient client = ConsulClient.create(vertx, new ConsulClientOptions().setHost(host).setPort(port));

        client.registerServiceAndAwait(new ServiceOptions().setPort(inventoryPort).setAddress(inventoryHost).setName("inventory-service").setId("inventory-service"));
        client.registerServiceAndAwait(new ServiceOptions().setPort(customerPort).setAddress(customerHost).setName("customer-service").setId("customer-service"));
        client.registerServiceAndAwait(new ServiceOptions().setPort(paymentPort).setAddress(paymentHost).setName("payment-service").setId("payment-service"));
        client.registerServiceAndAwait(new ServiceOptions().setPort(shipmentPort).setAddress(shipmentHost).setName("shipment-service").setId("shipment-service"));
    }
}