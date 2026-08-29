package com.mcmanuel.exception;

public class InvalidRoutingKeyException extends RuntimeException {
    public InvalidRoutingKeyException(String  message) {
        super(message);
    }
}
