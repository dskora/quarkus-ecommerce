package com.dskora.quarkus.ecommerce.payment.web;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.payment.api.web.*;
import com.dskora.quarkus.ecommerce.payment.domain.Payment;
import com.dskora.quarkus.ecommerce.payment.domain.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.UUID;

@Path("/payments")
public class PaymentController {
    @Inject
    PaymentService paymentService;

    @POST
    public CreatePaymentResponse requestPayment(CreatePaymentRequest paymentRequest) {
        Payment payment = paymentService.requestPayment(paymentRequest.getOrderId(), paymentRequest.getCustomerId(), new Money(paymentRequest.getAmount()), paymentRequest.getPaymentMethod());
        return new CreatePaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{orderId}/complete")
    public CompletePaymentResponse completePayment(UUID orderId) {
        Payment payment = paymentService.completePayment(orderId);
        return new CompletePaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{orderId}/cancel")
    public CancelPaymentResponse cancelPayment(UUID orderId) {
        Payment payment = paymentService.cancelPayment(orderId);
        return new CancelPaymentResponse(payment.getId());
    }

    @PUT
    @Path("/{orderId}/refund")
    public RefundPaymentResponse refundPayment(UUID orderId) {
        Payment payment = paymentService.refundPayment(orderId);
        return new RefundPaymentResponse(payment.getId());
    }
}