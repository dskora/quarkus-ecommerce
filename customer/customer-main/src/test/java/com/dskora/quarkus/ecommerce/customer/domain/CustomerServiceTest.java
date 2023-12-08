package com.dskora.quarkus.ecommerce.customer.domain;

import com.dskora.quarkus.ecommerce.common.domain.valueobject.Money;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTest
public class CustomerServiceTest {
    @InjectMock
    CustomerRepository customerRepository;

    @Inject
    CustomerService customerService;

    @Test
    public void findCustomer() {
        UUID id = UUID.randomUUID();

        Mockito.when(customerRepository.findById(id)).thenReturn(new Customer("Paul Jackson", new Money("100.00")));
        CustomerDto customerDto = customerService.findCustomer(id);

        assertThat(customerDto.getName(), equalTo("Paul Jackson"));
    }

    @Test
    public void reserveCustomerCredit() {
        UUID id = UUID.randomUUID();

        Mockito.when(customerRepository.findById(id)).thenReturn(new Customer("Paul Jackson", new Money("100.00")));
        Customer customer = customerService.reserveCredit(UUID.randomUUID(), id, new Money("10.00"));

        assertThat(customer.getCreditLimit().toString(), equalTo("90.00"));
    }

    @Test
    public void exceedsCustomerCreditLimit() {
        UUID id = UUID.randomUUID();

        Mockito.when(customerRepository.findById(id)).thenReturn(new Customer("Paul Jackson", new Money("100.00")));
        Customer customer = customerService.reserveCredit(UUID.randomUUID(), id, new Money("1000.00"));

        assertThat(customer.getCreditLimit().toString(), equalTo("100.00"));
    }
}
