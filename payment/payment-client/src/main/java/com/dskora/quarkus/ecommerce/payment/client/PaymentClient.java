package com.dskora.quarkus.ecommerce.payment.client;

import com.dskora.quarkus.ecommerce.payment.api.web.CreatePaymentRequest;
import com.dskora.quarkus.ecommerce.payment.api.web.CreatePaymentResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(baseUri = "stork://payment-service")
public interface PaymentClient {
    @POST
    @Path("/payments")
    CreatePaymentResponse requestPayment(CreatePaymentRequest requestPayment);

    @PUT
    @Path("/payments/{orderId}/complete")
    CreatePaymentResponse completePayment(@PathParam("orderId") UUID orderId);
}