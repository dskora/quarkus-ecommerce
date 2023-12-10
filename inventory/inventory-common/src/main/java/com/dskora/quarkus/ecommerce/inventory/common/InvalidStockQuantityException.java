package com.dskora.quarkus.ecommerce.inventory.common;

public class InvalidStockQuantityException extends RuntimeException {
    InvalidStockQuantityException(String msg) {
        super(msg);
    }
}
