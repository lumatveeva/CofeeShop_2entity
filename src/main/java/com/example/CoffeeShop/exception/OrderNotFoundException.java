package com.example.CoffeeShop.exception;

/**
 * Исключение при неуспешной попытке поиска заказа
 */
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
