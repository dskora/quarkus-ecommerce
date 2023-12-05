# Quarkus Ecommerce

## What are we doing here ?

- Event-Driven Architecture

- Domain Driven Design (DDD)

- SAGA Pattern: process & rollback ( compensating transactions )

- Outbox Pattern : Pulling Outbox Table With Scheduler , Saga Status

    - Cover Failure Scenarios :

        - Ensure idempotency using outbox table in each service

        - Prevent concurrency issues with optimistic looks & DB constraints

        - Kepp updating saga and order status for each operation


- Kafka Messaging Systems for SAGA Orchestration

## Prerequisite
- Framework: Quarkus
- Language: Java 17
- Build Tool: Gradle

```shell script
./gradlew
```

## Testing
### Scenario
Register a new customer with credit<br />
Set an inventory stock<br />
Customer uses its own account credit to finalize an order
 
> Events - CustomerCreated &#xb7; ProductRegisteredInStock &#xb7; (Saga Run) OrderCreated &#xb7; ProductStockReserved &#xb7; PaymentRequested &#xb7; CustomerCreditReserved &#xb7; PaymentCompleted &#xb7; ShipmentRequested
```bash
e2e-test/customer-uses-account-credit.sh
```