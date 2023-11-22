package com.dskora.quarkus.ecommerce.payment.controller;

import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.dto.PaymentRequest;
import com.dskora.quarkus.ecommerce.payment.dto.PaymentResponse;
import com.dskora.quarkus.ecommerce.payment.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/payments")
public class PaymentController {
    @Inject
    PaymentService paymentService;

    @POST
    public PaymentResponse registerCustomer(CustomerRequest paymentRequest) {
        Payment payment = paymentService.createPayment(paymentRequest);
        return new PaymentResponse(payment.getId());
    }
}