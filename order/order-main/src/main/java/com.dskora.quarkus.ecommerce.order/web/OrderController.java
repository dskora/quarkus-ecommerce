package com.dskora.quarkus.ecommerce.order.web;

import com.dskora.quarkus.ecommerce.order.api.web.CreateOrderRequest;
import com.dskora.quarkus.ecommerce.order.api.web.CreateOrderResponse;
import com.dskora.quarkus.ecommerce.order.domain.Order;
import com.dskora.quarkus.ecommerce.order.domain.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/orders")
public class OrderController {
    @Inject
    OrderService orderService;

    @POST
    public CreateOrderResponse placeOrder(CreateOrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return new CreateOrderResponse(order.getId());
    }

    @GET
    @Path("/test/{id}")
    public String test(@PathParam("id") String test) {
        return test;
    }
}