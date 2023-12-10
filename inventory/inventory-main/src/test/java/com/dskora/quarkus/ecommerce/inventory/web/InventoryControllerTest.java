package com.dskora.quarkus.ecommerce.inventory.web;

import com.dskora.quarkus.ecommerce.inventory.api.web.*;
import com.dskora.quarkus.ecommerce.inventory.domain.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTestResource(H2DatabaseTestResource.class)
@QuarkusTest
public class InventoryControllerTest {
    @Test
    public void registerAndFindCustomer() throws Exception  {
        RegisterProductStockRequest registerStock = new RegisterProductStockRequest(UUID.randomUUID(), 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(registerStock);

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/inventory/stock")
        .then()
            .statusCode(201);
    }

    @Test
    public void reserveStock() throws Exception  {
        UUID productId = UUID.randomUUID();
        RegisterProductStockRequest registerStock = new RegisterProductStockRequest(productId, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(registerStock);

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/inventory/stock")
        .then()
            .statusCode(201);

        ReserveProductStockRequest reserveStock = new ReserveProductStockRequest(UUID.randomUUID(), productId, 2);
        String payloadReserve = objectMapper.writeValueAsString(reserveStock);

        ReserveProductStockResponse reserveResponse =
            given()
                .contentType(ContentType.JSON)
                .body(payloadReserve)
            .when()
                .post("/inventory/stock/reserve")
            .then()
                .statusCode(200)
            .extract().as(ReserveProductStockResponse.class);

        assertThat(reserveResponse.getQuantityReserved(), equalTo(2));
        assertThat(reserveResponse.getQuantityLeft(), equalTo(8));

        StockResponse stock = given()
        .when()
            .get("/inventory/stock/" + productId)
        .then()
            .statusCode(200)
        .extract().as(StockResponse.class);

        assertThat(stock.getQuantity(), equalTo(8));
    }
}
