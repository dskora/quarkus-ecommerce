package com.dskora.quarkus.ecommerce.order.saga;


//@ApplicationScoped
//public class CustomerEventsConsumer {
//    @Inject
//    OrderService orderService;
//
//    @Incoming("customer.events")
//    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
//    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
//
//        return CompletableFuture.runAsync(() -> {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                String eventType = new String(message.getHeaders().lastHeader("type").value());
//                if (eventType.equals(CustomerCreditLimitExceededEvent.class.getSimpleName())) {
//                    CustomerCreditLimitExceededEvent customerCreditLimitExceededEvent = objectMapper.readValue(message.getPayload(), CustomerCreditLimitExceededEvent.class);
//                    orderService.rejectOrder(customerCreditLimitExceededEvent);
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