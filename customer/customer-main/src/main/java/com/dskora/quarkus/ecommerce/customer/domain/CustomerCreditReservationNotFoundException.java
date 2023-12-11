package com.dskora.quarkus.ecommerce.customer.domain;

public class CustomerCreditReservationNotFoundException extends RuntimeException {
    CustomerCreditReservationNotFoundException(String msg) {
        super(msg);
    }
}