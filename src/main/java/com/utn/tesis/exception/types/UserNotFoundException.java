package com.utn.tesis.exception.types;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found");
    }
}
