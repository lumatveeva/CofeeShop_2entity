package com.example.CoffeeShop.generators;

import com.example.CoffeeShop.entity.order.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * Класс для кастомной генерации заказов с случайными данными
 */
@Component
public class OrderGenerator {
    private final Random random = new Random();

    public Order generateOrder() {
        return new Order.Builder()
                .withCustomerId(UUID.randomUUID())
                .withEmployeeId(UUID.randomUUID())
                .withDeliveryTime(LocalDateTime.now().plusMinutes(random.nextInt(10,40)))
                .withProductId(UUID.randomUUID())
                .withProductCost(random.nextLong(50,200))
                .build();
    }
}
