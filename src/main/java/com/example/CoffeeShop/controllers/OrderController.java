package com.example.CoffeeShop.controllers;

import com.example.CoffeeShop.entity.event.OrderEvent;
import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.generators.OrderGenerator;
import com.example.CoffeeShop.services.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceImpl orderService;
    private final OrderGenerator generator;

    public OrderController(OrderServiceImpl orderService, OrderGenerator generator) {
        this.orderService = orderService;
        this.generator = generator;
    }

    @Operation(summary = "Осуществить поиск всех заказов")
    @GetMapping("/all")
    @Tag(name = "Получение значений")
    public List<Order> getAllOrder() {
        return orderService.findAllOrder();
    }

    @Operation(summary = "Осуществить поиск заказа по идентификатору")
    @GetMapping("getOrder/{id}")
    @Tag(name = "Получение значений")
    public Order getOrderById(@Parameter(description = "Идентификатор заказа")
                              @PathVariable("id") int orderId) {
        return orderService.findOrder(orderId);
    }

    @Operation(summary = "Осуществить поиск истории заказа по идентификатору")
    @GetMapping("/orderHistory/{id}")
    @Tag(name = "Получение значений")
    public List<OrderEvent> getOrderEventsByOrderId(@Parameter(description = "Идентификатор заказа")
                                                    @PathVariable("id") int orderId) {
        return orderService.findEventsByOrderId(orderId);
    }

    @Operation(summary = "Зарегестрировать новый заказ")
    @PostMapping("/registrate")
    @Tag(name = "Смена состояния заказа")
    public Order registrationOrder(@RequestBody Order order) {
        return orderService.registrationOrder(order);
    }

    @Operation(summary = "Сгенерировать и зарегистировать новый заказ")
    @PostMapping("/generate")
    @Tag(name = "Смена состояния заказа")
    public Order generateOrder() {
        Order order = generator.generateOrder();
        return orderService.registrationOrder(order);
    }

    @Operation(summary = "Отменить заказ")
    @PostMapping("/cancel/{id}")
    @Tag(name = "Смена состояния заказа")
    public void cancelOrder(@Parameter(description = "Идентификатор заказа") @PathVariable("id") int orderId,
                               @Parameter(description = "Причина отмены заказа") @RequestParam String cancelReason) {
        if (cancelReason == null || cancelReason.trim().isEmpty()) {
            throw new IllegalStateException("Причина отмены заказа должна быть заполнена");
        }
        orderService.cancelOrder(orderId, cancelReason);
    }

    @Operation(summary = "Взять заказ в работу")
    @PostMapping("/accept/{id}")
    @Tag(name = "Смена состояния заказа")
    public void acceptOrder(@PathVariable("id") int orderId) {
        orderService.acceptOrder(orderId);
    }

    @Operation(summary = "Подготовить заказ к выдаче")
    @PostMapping("/readyToDelivery/{id}")
    @Tag(name = "Смена состояния заказа")
    public void readyToDeliveryOrder(@PathVariable("id") int orderId) {
        orderService.readyToDeliveryOrder(orderId);
    }

    @Operation(summary = "Выдать заказ")
    @PostMapping("/deliver/{id}")
    @Tag(name = "Смена состояния заказа")
    public void deliverOrder(@PathVariable("id") int orderId) {
        orderService.deliverOrder(orderId);
    }

}
