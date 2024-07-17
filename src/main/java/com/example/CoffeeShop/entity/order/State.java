package com.example.CoffeeShop.entity.order;

public enum State {
    REGISTER("зарегистрирован"),
    CANCELLED("отменен"),
    ACCEPT("взят в работу"),
    READY_TO_DELIVERY("готов к выдаче"),
    DELIVERED("выдан");

    private final String description;

    State(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

