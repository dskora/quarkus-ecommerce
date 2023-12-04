package com.dskora.quarkus.ecommerce.order.saga;

//@ApplicationScoped
//public class ShipmentEventsConsumer {
//    @Inject
//    OrderService orderService;
//
//    @Incoming("shipment.events")
//    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
//    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
//        return CompletableFuture.runAsync(() -> {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String eventType = new String(message.getHeaders().lastHeader("type").value());
//                if (eventType.equals(ShipmentDeliveredEvent.class.getSimpleName())) {
//                    ShipmentDeliveredEvent shipmentDeliveredEvent = objectMapper.readValue(message.getPayload(), ShipmentDeliveredEvent.class);
//                    orderService.completeOrder(shipmentDeliveredEvent.getOrderId());
//                }
//
//                if (eventType.equals(ShipmentCancelledEvent.class.getSimpleName())) {
//                    ShipmentCancelledEvent shipmentCancelledEvent = objectMapper.readValue(message.getPayload(), ShipmentCancelledEvent.class);
//                    orderService.rejectOrder(shipmentCancelledEvent);
//                }
//
//                message.ack();
//            } catch(JsonMappingException e) {
//                System.out.println(e.getMessage());
//            } catch(JsonProcessingException e) {
//                System.out.println(e.getMessage());
//                message.nack(e);
//            }
//        });
//    }
//}