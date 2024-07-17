package com.example.CoffeeShop.services;

import com.example.CoffeeShop.entity.event.EventType;
import com.example.CoffeeShop.entity.event.OrderEvent;
import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.repository.EventRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис для создания событий по изменению статуса заказа
 */
@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Метод для сохранения нового события
     * @param event Новое событие
     */
    public void save(OrderEvent event) {
        eventRepository.save(event);
    }

    /**
     * Метод для создания нового события с статусом 'Заказ зарегистрирован'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent registrationsEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order)
                .withEventType(EventType.ORDER_REGISTERED)
                .withCustomerId(order.getCustomerId())
                .withDeliveryTime(order.getDeliveryTime())
                .withEmployeeId(order.getEmployeeId())
                .withProductId(order.getProductId())
                .withProductCost(order.getProductCost())
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ отменен'
     * @param order Заказ, статус которого был изменен
     * @param cancelledReason Причина для отмены заказа
     * @return объект OrderEvent
     */
    public OrderEvent cancelledEvent(Order order, String cancelledReason) {
        return new OrderEvent.Builder()
                .withOrder(order)
                .withEmployeeId(order.getEmployeeId())
                .withCancelledReason(cancelledReason)
                .withEventType(EventType.ORDER_CANCELLED)
                .build();
    }


    /**
     * Метод для создания нового события с статусом 'Заказ взят в работу'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent acceptedEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order)
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_ACCEPTING)
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ готов к выдаче'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent readyToDeliveryEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order)
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_READY_TO_DELIVERY)
                .build();
    }

    /**
     * Метод для создания нового события с статусом 'Заказ выдан'
     * @param order Заказ, статус которого был изменен
     * @return объект OrderEvent
     */
    public OrderEvent deliverEvent(Order order) {
        return new OrderEvent.Builder()
                .withOrder(order)
                .withEmployeeId(order.getEmployeeId())
                .withEventType(EventType.ORDER_DELIVERED)
                .build();
    }
}
