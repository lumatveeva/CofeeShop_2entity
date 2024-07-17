package com.example.CoffeeShop.services;

import com.example.CoffeeShop.entity.event.OrderEvent;
import com.example.CoffeeShop.entity.order.Order;

/**
 * Интерфейс для сервисного класса заказов
 */
interface OrderService {
    void publishEvent(OrderEvent event);
    Order findOrder(int id);

}