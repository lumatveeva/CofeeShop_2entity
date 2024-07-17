package com.example.CoffeeShop;

import com.example.CoffeeShop.entity.order.Order;
import com.example.CoffeeShop.entity.order.State;
import com.example.CoffeeShop.exception.InvalidOrderStateException;
import com.example.CoffeeShop.repository.OrderRepository;
import com.example.CoffeeShop.services.EventService;
import com.example.CoffeeShop.services.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {OrderServiceImpl.class})
public class OrderServiceTest {

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    public EventService eventService;

    @Test
    public void deliveryFromCancelledOrderTest() throws IOException {
        Order orderByCancelledState = getOrder("OrderByCancelledState.json");
        orderByCancelledState.setState(State.CANCELLED);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderByCancelledState));
        assertThrows(InvalidOrderStateException.class, () ->
                orderService.deliverOrder(10));
    }

    @Test
    public void acceptFromDeliveredOrderTest() throws IOException {
        Order orderByDeliveredState = getOrder("OrderByDeliveredState.json");
        orderByDeliveredState.setState(State.DELIVERED);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderByDeliveredState));
        assertThrows(InvalidOrderStateException.class, () ->
                orderService.acceptOrder(10));
    }

    @Test
    public void readyToBeDeliveredFromRegisterOrderTest() throws IOException {
        Order orderByRegisterState = getOrder("OrderByRegisterState.json");
        orderByRegisterState.setState(State.REGISTER);
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(orderByRegisterState));
        assertThrows(InvalidOrderStateException.class, () ->
                orderService.readyToDeliveryOrder(10));
    }

    private Order getOrder(String jsonName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(jsonName);
        File jsonFile = new File(resource.getFile());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonFile, Order.class);
    }
}
