package com.example.CoffeeShop.services;

import com.example.CoffeeShop.entity.event.OrderEvent;
import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.entity.order.State;
import com.example.CoffeeShop.exception.InvalidOrderStateException;
import com.example.CoffeeShop.exception.OrderNotFoundException;
import com.example.CoffeeShop.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления заказами
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public final EventService eventService;
    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository orderRepository, EventService eventService) {
        this.orderRepository = orderRepository;
        this.eventService = eventService;
    }

    /**
     * Метод 'публикует'- записывает в БД произошедшее событие
     *
     * @param event Событие, которое необходимо записать
     */
    @Override
    public void publishEvent(OrderEvent event) {
        eventService.save(event);
        log.info("Событие заказа с id " + event.getOrder().getId() + " сохранено");
    }

    /**
     * Поиск заказа по его id
     *
     * @param id Идентификатор заказа
     * @return объект Order
     */
    @Override
    public Order findOrder(int id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Заказ с id " + id + " не найден"));
    }

    /**
     * Поиск всех событий заказа по его id
     *
     * @param id Идентификатор заказа
     * @return List<OrderEvent> Список всех событий найденного заказа
     */
    public List<OrderEvent> findEventsByOrderId(int id) {
        Order order = findOrder(id);
        log.info("Поиск всех событий по заказу с id " + id + "  осуществлен");
        return order.getOrderEvents();
    }

    /**
     * Поиск всех заказов, записанных в БД
     *
     * @return List<Order> Списов всех заказов
     */
    public List<Order> findAllOrder() {
        log.info("Поиск всех заказов, записанных в БД");
        return orderRepository.findAll();
    }

    /**
     * Метод регистрации заказа, сохраняет заказ в БД, публикует событие о регистрации заказа
     *
     * @param order Заказ, который необходимо сохранить
     * @return объект Order
     */
    public Order registrationOrder(Order order) {
        order.setState(State.REGISTER);
        orderRepository.save(order);
        OrderEvent event = eventService.registrationsEvent(order);
        publishEvent(event);
        log.info("Заказ под номером " + order.getId() + " зарегистирован");
        return order;
    }

    /**
     * Метод для отмены заказа, меняет статус заказа на 'отменен', записывает изменение в таблицу Order
     * и публикует событие о смене статуса
     *
     * @param orderId         Идентификатор заказа которые необходимо отменить
     * @param cancelledReason Причина отмены заказа
     * @throws InvalidOrderStateException Если с текущего статуса заказа не возможности перейти на желаемый
     */
    public void cancelOrder(int orderId, String cancelledReason) {
        Order cancelledOrder = findOrder(orderId);
        if (cancelledOrder.getState().equals(State.DELIVERED) && cancelledOrder.getState().equals(State.CANCELLED)) {
            throw new InvalidOrderStateException("Неверный статус заказа. Данный заказ выдан или отменен");
        } else {
            cancelledOrder.setState(State.CANCELLED);
            cancelledOrder.setCancelledReason(cancelledReason);
            orderRepository.save(cancelledOrder);
            OrderEvent event = eventService.cancelledEvent(cancelledOrder, cancelledReason);
            publishEvent(event);
            log.info("Заказ под номером " + cancelledOrder.getId() + " отменен");
        }
    }

    /**
     * Метод устанавливающий статус заказа 'взят в работу', записывает изменение в таблицу Order
     * и публикует событие о смене статуса
     *
     * @param orderId Идентификатор заказа статус которого необходимо сменить
     * @throws InvalidOrderStateException Если с текущего статуса заказа не возможности перейти на желаемый
     */
    public void acceptOrder(int orderId) {
        Order acceptedOrder = findOrder(orderId);
        if (!acceptedOrder.getState().equals(State.REGISTER)) {
            throw new InvalidOrderStateException("Неверный статус заказа. Требуемый статус заказа: " + State.REGISTER
                    + ", текущий статус: " + acceptedOrder.getState());
        } else {
            acceptedOrder.setState(State.ACCEPT);
            orderRepository.save(acceptedOrder);
            OrderEvent event = eventService.acceptedEvent(acceptedOrder);
            publishEvent(event);
            log.info("Заказ под номером " + acceptedOrder.getId() + " взят в работу");
        }
    }

    /**
     * Метод устанавливающий статус заказа 'готов к выдаче', записывает изменение в таблицу Order
     * и публикует событие о смене статуса
     *
     * @param orderId Идентификатор заказа статус которого необходимо сменить
     * @throws InvalidOrderStateException Если с текущего статуса заказа не возможности перейти на желаемый
     */
    public void readyToDeliveryOrder(int orderId) {
        Order readyToDeliveryOrder = findOrder(orderId);
        if (!readyToDeliveryOrder.getState().equals(State.ACCEPT)) {
            throw new InvalidOrderStateException("Неверный статус заказа. Требуемый статус заказа: " + State.ACCEPT
                    + ", текущий статус: " + readyToDeliveryOrder.getState());
        } else {
            readyToDeliveryOrder.setState(State.READY_TO_DELIVERY);
            orderRepository.save(readyToDeliveryOrder);
            OrderEvent event = eventService.readyToDeliveryEvent(readyToDeliveryOrder);
            publishEvent(event);
            log.info("Заказ под номером " + readyToDeliveryOrder.getId() + " готов к выдаче");
        }
    }

    /**
     * Метод устанавливающий статус заказа 'выдан', записывает изменение в таблицу Order и публикует событие
     * о смене статуса
     *
     * @param orderId Идентификатор заказа статус которого необходимо сменить
     * @throws InvalidOrderStateException Если с текущего статуса заказа не возможности перейти на желаемый
     */
    public void deliverOrder(int orderId) {
        Order deliveredOrder = findOrder(orderId);
        if (!deliveredOrder.getState().equals(State.READY_TO_DELIVERY)) {
            throw new InvalidOrderStateException("Неверный статус заказа. Требуемый статус заказа: " + State.READY_TO_DELIVERY
                    + ", текущий статус: " + deliveredOrder.getState());
        } else {
            deliveredOrder.setState(State.DELIVERED);
            orderRepository.save(deliveredOrder);
            OrderEvent event = eventService.deliverEvent(deliveredOrder);
            publishEvent(event);
            log.info("Заказ под номером " + deliveredOrder.getId() + " выдан");
        }
    }
}
