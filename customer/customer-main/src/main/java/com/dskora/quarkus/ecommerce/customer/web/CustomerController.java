package com.dskora.quarkus.ecommerce.customer.web;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.api.web.CreateCustomerRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.CreateCustomerResponse;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditResponse;
import com.dskora.quarkus.ecommerce.customer.domain.Customer;
import com.dskora.quarkus.ecommerce.customer.domain.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/customers")
public class CustomerController {
    @Inject
    CustomerService customerService;

    @POST
    public CreateCustomerResponse registerCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest.getName(), new Money(customerRequest.getCreditLimit()));
        return new CreateCustomerResponse(customer.getId());
    }

    @POST
    @Path("/credit")
    public ReserveCustomerCreditResponse reserveCustomerCredit(ReserveCustomerCreditRequest reserveCustomerCreditRequest) {
        Customer customer = customerService.reserveCredit(reserveCustomerCreditRequest.getOrderId(), reserveCustomerCreditRequest.getCustomerId(), new Money(reserveCustomerCreditRequest.getAmount()));
        return new ReserveCustomerCreditResponse(customer.getId());
    }
}