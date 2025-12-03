package com.esportslan.microservices.live_updates.exceptions;

public class BadRequestErrorException extends RuntimeException{

    public BadRequestErrorException(String message) {
        super(message);
    }

    public BadRequestErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
