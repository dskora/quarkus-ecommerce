package com.dskora.quarkus.ecommerce.customer.web;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.api.web.*;
import com.dskora.quarkus.ecommerce.customer.domain.Customer;
import com.dskora.quarkus.ecommerce.customer.domain.CustomerDto;
import com.dskora.quarkus.ecommerce.customer.domain.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.UUID;

@Path("/customers")
public class CustomerController {
    @Inject
    CustomerService customerService;

    @POST
    @ResponseStatus(201)
    public CreateCustomerResponse registerCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest.getName(), new Money(customerRequest.getCreditLimit()));
        return new CreateCustomerResponse(customer.getId());
    }

    @POST
    @Path("/credit/reserve")
    public ReserveCustomerCreditResponse reserveCustomerCredit(ReserveCustomerCreditRequest reserveCustomerCreditRequest) {
        Customer customer = customerService.reserveCredit(reserveCustomerCreditRequest.getOrderId(), reserveCustomerCreditRequest.getCustomerId(), new Money(reserveCustomerCreditRequest.getAmount()));
        return new ReserveCustomerCreditResponse(customer.getId());
    }

    @POST
    @Path("/credit/release")
    public ReleaseCustomerCreditResponse releaseCustomerCredit(ReleaseCustomerCreditRequest reserveCustomerCreditRequest) {
        Customer customer = customerService.releaseCredit(reserveCustomerCreditRequest.getCustomerId(), reserveCustomerCreditRequest.getOrderId());
        return new ReleaseCustomerCreditResponse(customer.getId());
    }

    @GET
    @Path("/{id}")
    public CustomerDto findCustomer(@PathParam("id") UUID id) {
        return this.customerService.findCustomer(id);
    }
}