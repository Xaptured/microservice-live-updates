package com.esportslan.microservices.live_updates.exceptions;

public class InternalErrorException extends RuntimeException{

    public InternalErrorException(String message) {
        super(message);
    }

    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
