package com.dskora.quarkus.ecommerce.payment.controller;

import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.dto.CreatePaymentRequest;
import com.dskora.quarkus.ecommerce.payment.dto.CreatePaymentResponse;
import com.dskora.quarkus.ecommerce.payment.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/payments")
public class PaymentController {
    @Inject
    PaymentService paymentService;

    @POST
    public CreatePaymentResponse requestPayment(CreatePaymentRequest paymentRequest) {
        Payment payment = paymentService.requestPayment(paymentRequest);
        return new CreatePaymentResponse(payment.getId());
    }
}