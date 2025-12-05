package com.esportslan.microservices.live_updates.exceptions;

public class EventConsumerException extends RuntimeException {
    public EventConsumerException(String message) {
        super(message);
    }

    public EventConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}
