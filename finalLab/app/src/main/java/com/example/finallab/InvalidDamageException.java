package com.example.finallab;

public class InvalidDamageException extends Exception{
    public InvalidDamageException(String message) {
        super(message);
    }

    public InvalidDamageException(String message, Throwable cause) {
        super(message, cause);
    }
}
