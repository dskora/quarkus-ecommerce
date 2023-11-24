package com.dskora.quarkus.ecommerce.order.controller;

import com.dskora.quarkus.ecommerce.order.domain.Order;
import com.dskora.quarkus.ecommerce.order.dto.CreateOrderRequest;
import com.dskora.quarkus.ecommerce.order.dto.CreateOrderResponse;
import com.dskora.quarkus.ecommerce.order.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/orders")
public class OrderController {
    @Inject
    OrderService orderService;

    @POST
    public CreateOrderResponse placeOrder(CreateOrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return new CreateOrderResponse(order.getId());
    }
}