package com.dskora.quarkus.ecommerce.payment.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
    public Payment findByOrderId(UUID orderId){
        return find("orderId", orderId).firstResult();
    }
}
