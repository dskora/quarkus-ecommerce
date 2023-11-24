package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreditReservedEvent;
import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.common.domain.api.CustomerCreatedEvent;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "customers")
public class Customer {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String name;

    @Embedded
    private Money creditLimit;

    protected Customer() {}

    public Customer(String name, Money creditLimit) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.creditLimit = creditLimit;
    }

    public static ResultWithEvents<Customer> create(String name, Money creditLimit)
    {
        Customer customer = new Customer(name, creditLimit);
        CustomerCreatedEvent event = new CustomerCreatedEvent(name, creditLimit);

        return new ResultWithEvents<>(customer, event);
    }

    public ResultWithEvents<Customer> reserveCredit(Money total) throws CustomerCreditLimitExceededException {
        if (total.isGreaterThan(this.creditLimit)) {
            throw new CustomerCreditLimitExceededException();
        }

        this.creditLimit = this.creditLimit.subtract(total);
        CustomerCreditReservedEvent event = new CustomerCreditReservedEvent(total);

        return new ResultWithEvents<>(this, event);
    }

    public UUID getId() {
        return id;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }
}
