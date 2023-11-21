package com.dskora.quarkus.ecommerce.common.domain.event;

import java.util.Arrays;
import java.util.List;

public class ResultWithEvents<T> {
  public final T result;

  public final List<DomainEvent> events;

  public ResultWithEvents(T result, List<DomainEvent> events) {
    this.result = result;
    this.events = events;
  }

  public ResultWithEvents(T result, DomainEvent... events) {
    this.result = result;
    this.events = Arrays.asList(events);
  }
}
