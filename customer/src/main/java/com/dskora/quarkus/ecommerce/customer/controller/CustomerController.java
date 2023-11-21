package com.dskora.quarkus.ecommerce.customer.controller;

import com.dskora.quarkus.ecommerce.customer.domain.Customer;
import com.dskora.quarkus.ecommerce.customer.dto.CustomerRequest;
import com.dskora.quarkus.ecommerce.customer.dto.CustomerResponse;
import com.dskora.quarkus.ecommerce.customer.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/customers")
public class CustomerController {
    @Inject
    CustomerService customerService;

    @POST
    public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);
        return new CustomerResponse(customer.getId());
    }
}