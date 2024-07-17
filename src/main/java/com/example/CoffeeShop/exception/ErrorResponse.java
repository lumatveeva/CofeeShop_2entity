package com.example.CoffeeShop.exception;

/**
 * Класс представляет ответ об ошибке, содержащий сообщение с описанием ошибки.
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
