package com.dskora.quarkus.ecommerce.payment.controller;

import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.dto.*;
import com.dskora.quarkus.ecommerce.payment.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.util.UUID;

@Path("/payments")
public class PaymentController {
    @Inject
    PaymentService paymentService;

    @POST
    public CreatePaymentResponse requestPayment(CreatePaymentRequest paymentRequest) {
        Payment payment = paymentService.requestPayment(paymentRequest);
        return new CreatePaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{id}/complete")
    public CompletePaymentResponse completePayment(UUID id) {
        Payment payment = paymentService.completePayment(id);
        return new CompletePaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{id}/cancel")
    public CancelPaymentResponse cancelPayment(UUID id) {
        Payment payment = paymentService.cancelPayment(id);
        return new CancelPaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{id}/refund")
    public RefundPaymentResponse refundPayment(UUID id) {
        Payment payment = paymentService.refundPayment(id);
        return new RefundPaymentResponse(payment.getId());
    }
}