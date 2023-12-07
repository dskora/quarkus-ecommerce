package com.dskora.quarkus.ecommerce.customer.web;

import com.dskora.quarkus.ecommerce.customer.api.web.CreateCustomerRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.CreateCustomerResponse;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditRequest;
import com.dskora.quarkus.ecommerce.customer.api.web.ReserveCustomerCreditResponse;
import com.dskora.quarkus.ecommerce.customer.domain.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTestResource(H2DatabaseTestResource.class)
@QuarkusTest
public class CustomerControllerTest {
    @Test
    public void registerAndFindCustomer() throws Exception  {
        CreateCustomerRequest request = new CreateCustomerRequest("Paul Jackson", BigDecimal.valueOf(100.0));
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(request);

        // Register Customer
        CreateCustomerResponse response =
            given()
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/customers")
            .then()
                .statusCode(201)
            .extract().as(CreateCustomerResponse.class);

        // Validate Customer
        CustomerDto customer = given()
            .contentType(ContentType.JSON)
            .when().get("/customers/" + response.getId())
            .as(CustomerDto.class);

        assertThat(customer.getName(), equalTo(request.getName()));
    }

    @Test
    public void reserveCustomerCredit() throws Exception  {
        CreateCustomerRequest request = new CreateCustomerRequest("Paul Jackson", BigDecimal.valueOf(100.0));
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(request);

        // Register Customer
        CreateCustomerResponse response =
            given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/customers")
                .then()
                .statusCode(201)
                .extract().as(CreateCustomerResponse.class);

        ReserveCustomerCreditRequest reserveCreditRequest = new ReserveCustomerCreditRequest(UUID.randomUUID(), response.getId(), BigDecimal.valueOf(10.0));
        String reservePayload = objectMapper.writeValueAsString(reserveCreditRequest);

        // Reserve Credit
        ReserveCustomerCreditResponse reserveResponse =
            given()
                .contentType(ContentType.JSON)
                .body(reservePayload)
                .when()
                .post("/customers/credit")
                .then()
                .statusCode(200)
                .extract().as(ReserveCustomerCreditResponse.class);

        // Validate Customer
        CustomerDto customer = given()
            .contentType(ContentType.JSON)
            .when().get("/customers/" + response.getId())
            .as(CustomerDto.class);

        assertThat(customer.getCreditLimit().toString(), equalTo("90.00"));
    }
}
