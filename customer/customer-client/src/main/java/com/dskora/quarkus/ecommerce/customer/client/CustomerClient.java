package com.dskora.quarkus.ecommerce.customer.client;

import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "stork://customer-service")
public interface CustomerClient {
    @POST
    @Path("/customers/credit")
    ReserveCustomerCreditResponse reserveCredit(ReserveCustomerCreditRequest reserveCustomerCreditRequest);
}