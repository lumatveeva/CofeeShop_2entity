package com.example.CoffeeShop.exception;

/**
 * Исключение в случае не совпадения текущего и ожидаемого статуса заказа
 */
public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}

