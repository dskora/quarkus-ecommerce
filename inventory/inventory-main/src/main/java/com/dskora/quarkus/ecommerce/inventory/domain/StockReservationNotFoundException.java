package com.dskora.quarkus.ecommerce.inventory.domain;

public class StockReservationNotFoundException extends RuntimeException {
    StockReservationNotFoundException(String msg) {
        super(msg);
    }
}