#!/bin/bash

productId='"3fa85f64-5717-4562-b3fc-2c963f66afa6"'

echo -e "Resetting..."
docker exec -it mysql mysql -u root -proot quarkus_ecommerce -e 'delete from events' > /dev/null

echo -e "Register new customer"
customerId=$(curl -s -X POST localhost:8085/customers -H "Content-type: application/json" -d '{"name": "Tom Andrews", "creditLimit": 100}' | jq '.id')
echo -e "Set product inventory stock"
curl -s -o /dev/null -X POST localhost:8087/inventory/stock -H "Content-type: application/json" -d '{"productId": '$productId', "quantity": "100"}'
echo -e "Create new order"
curl -s -o /dev/null -X POST localhost:8084/orders -H "Content-type: application/json" -d '{
  "customerId": '$customerId',
  "productId": '$productId',
  "total": 200,
  "quantity": 2,
  "paymentDetails": {
    "paymentMethod": "ACCOUNT_CREDIT"
  },
  "shipmentDetails": {
    "shipmentProvider": "DHL",
    "shipmentAddress": "7 Kings Road, London, UK"
  }
}'

echo -e "Wait to finalize..."
sleep 5
echo -e "Show events"
docker exec -it mysql mysql -u root -proot quarkus_ecommerce -e 'SELECT type FROM events ORDER BY created_at ASC' 2>/dev/null