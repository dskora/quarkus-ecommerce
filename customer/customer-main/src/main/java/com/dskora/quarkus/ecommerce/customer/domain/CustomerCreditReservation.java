package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Entity(name = "customer_credit_reservation")
public class CustomerCreditReservation {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID orderId;

    @Embedded
    private Money amount;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    protected CustomerCreditReservation() {}

    protected CustomerCreditReservation(Customer customer, UUID orderId, Money amount) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.orderId = orderId;
        this.amount = amount;
    }
}
