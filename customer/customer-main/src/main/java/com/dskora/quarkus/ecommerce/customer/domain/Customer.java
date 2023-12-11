package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.event.ResultWithEvents;
import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreatedEvent;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditReleasedEvent;
import com.dskora.quarkus.ecommerce.customer.event.CustomerCreditReservedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity(name = "customers")
public class Customer {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String name;

    @Embedded
    private Money creditLimit;

    @OneToMany(mappedBy="customer", cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerCreditReservation> creditReservations = new HashSet<CustomerCreditReservation>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant lastUpdatedAt;

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

    public ResultWithEvents<Customer> reserveCredit(UUID orderId, Money amount) throws CustomerCreditLimitExceededException {
        if (amount.isGreaterThan(this.creditLimit)) {
            throw new CustomerCreditLimitExceededException();
        }

        this.creditLimit = this.creditLimit.subtract(amount);
        this.creditReservations.add(new CustomerCreditReservation(this, orderId, amount));

        CustomerCreditReservedEvent event = new CustomerCreditReservedEvent(orderId, amount);

        return new ResultWithEvents<>(this, event);
    }

    public ResultWithEvents<Customer> releaseCredit(UUID orderId)
    {
        CustomerCreditReservation reservation = reservationByOrderId(orderId);

        creditLimit = this.creditLimit.add(reservation.getAmount());
        creditReservations.remove(reservationByOrderId(orderId));

        CustomerCreditReleasedEvent event = new CustomerCreditReleasedEvent(orderId, reservation.getAmount());

        return new ResultWithEvents<>(this, event);
    }

    private CustomerCreditReservation reservationByOrderId(UUID orderId) {
        return creditReservations
            .stream()
            .filter(r -> r.getOrderId().equals(orderId))
            .findFirst()
            .orElseThrow(() -> new CustomerCreditReservationNotFoundException("Cannot find CreditReservation by OrderId=" + orderId));
    }
}
