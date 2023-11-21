package com.dskora.quarkus.ecommerce.common.domain.valueobject;

public abstract class BaseId<T> {

    private final T value;

    protected BaseId(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
