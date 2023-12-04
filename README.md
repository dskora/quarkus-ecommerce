# Quarkus Ecommerce

## Getting started

## Prerequisite
- Framework: Quarkus
- Language: Java 17
- Build Tool: Gradle

```shell script
./gradlew
```

## Testing
Customer uses its own credit as a payment method
```bash
# Example product id
productId='"3fa85f64-5717-4562-b3fc-2c963f66afa6"'

customerId=$(curl -s -X POST localhost:8085/customers -H "Content-type: application/json" -d '{"name": "Tom Andrews", "creditLimit": 1000}' | jq '.id')
curl -X POST localhost:8087/inventory/products -H "Content-type: application/json" -d '{"productId": '$productId', "stocks": "100"}'
curl -X POST localhost:8084/orders -H "Content-type: application/json" -d '{
  "customerId": '$customerId',
  "productId": '$productId',
  "total": 100,
  "paymentDetails": {
    "paymentMethod": "ACCOUNT_CREDIT"
  },
  "shipmentDetails": {
    "shipmentProvider": "DHL",
    "address": "7 Kings Road, London, UK"
  }
}'
```