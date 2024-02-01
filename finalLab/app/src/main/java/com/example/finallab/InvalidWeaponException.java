package com.example.finallab;

public class InvalidWeaponException extends Exception{
    public InvalidWeaponException(String message) {
        super(message);
    }

    public InvalidWeaponException(String message, Throwable cause) {
        super(message, cause);
    }
}
