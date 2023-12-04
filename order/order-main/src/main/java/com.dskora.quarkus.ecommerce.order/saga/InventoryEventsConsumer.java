package com.dskora.quarkus.ecommerce.order.saga;

//@ApplicationScoped
//public class InventoryEventsConsumer {
//    @Inject
//    OrderService orderService;
//
//    @Incoming("inventory.events")
//    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
//    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
//        return CompletableFuture.runAsync(() -> {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String eventType = new String(message.getHeaders().lastHeader("type").value());
//                if (eventType.equals(ProductOutOfStockEvent.class.getSimpleName())) {
//                    ProductOutOfStockEvent productOutOfStockEvent = objectMapper.readValue(message.getPayload(), ProductOutOfStockEvent.class);
//                    orderService.rejectOrder(productOutOfStockEvent);
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