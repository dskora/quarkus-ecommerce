package com.dskora.quarkus.ecommerce.inventory.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.inventory.common.StockQuantity;
import com.dskora.quarkus.ecommerce.inventory.event.ProductRegisteredInStockEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class StockTest {
    @Test
    public void createStock() throws Exception  {
        ResultWithEvents<Stock> stockResultWithEvents = Stock.create(UUID.randomUUID(), new StockQuantity(10));
        ProductRegisteredInStockEvent event = (ProductRegisteredInStockEvent) stockResultWithEvents.events.get(0);

        assertThat(event.getQuantity().getQuantity(), equalTo(10));
    }

    @Test
    public void reserveStock() throws Exception  {
        ResultWithEvents<Stock> stock = Stock.create(UUID.randomUUID(), new StockQuantity(10));
        ResultWithEvents<Stock> stockResultWithEvents = stock.result.reserveStock(UUID.randomUUID(), new StockQuantity(2));

        assertThat(stockResultWithEvents.result.getQuantity().getQuantity(), equalTo(8));
    }

    @Test
    public void exceedStockCredit() throws Exception  {
        ResultWithEvents<Stock> stock = Stock.create(UUID.randomUUID(), new StockQuantity(2));

        Throwable exception = assertThrows(ProductOutOfStockException.class,
            () -> {
                stock.result.reserveStock(UUID.randomUUID(), new StockQuantity(4));
        });
    }
}
