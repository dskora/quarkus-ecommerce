package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreatedEvent;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditReservedEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CustomerTest {
    @Test
    public void createCustomer() throws Exception  {
        ResultWithEvents<Customer> customerResultWithEvents = Customer.create("Paul Jackson", new Money("100.00"));
        CustomerCreatedEvent event = (CustomerCreatedEvent) customerResultWithEvents.events.get(0);

        assertThat(event.getName(), equalTo("Paul Jackson"));
        assertThat(customerResultWithEvents.result.getName(), equalTo("Paul Jackson"));
    }

    @Test
    public void reserveCustomerCredit() throws Exception  {
        ResultWithEvents<Customer> customerResultWithEvents = Customer.create("Paul Jackson", new Money("100.00"));
        ResultWithEvents<Customer> customerReserveResultWithEvents = customerResultWithEvents.result.reserveCredit(UUID.randomUUID(), new Money("10.00"));
        CustomerCreditReservedEvent event = (CustomerCreditReservedEvent) customerReserveResultWithEvents.events.get(0);

        assertThat(event.getTotal().getAmount().toString(), equalTo("10.00"));
        assertThat(customerReserveResultWithEvents.result.getCreditLimit().getAmount().toString(), equalTo("90.00"));
    }

    @Test
    public void exceedCustomerCredit() throws Exception  {
        ResultWithEvents<Customer> customerResultWithEvents = Customer.create("Paul Jackson", new Money("10.00"));

        Throwable exception = assertThrows(CustomerCreditLimitExceededException.class,
            () -> {
                customerResultWithEvents.result.reserveCredit(UUID.randomUUID(), new Money("100.00"));
        });
    }
}
