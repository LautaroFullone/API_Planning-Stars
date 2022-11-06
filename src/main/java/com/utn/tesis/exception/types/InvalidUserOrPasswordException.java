package com.utn.tesis.exception.types;

public class InvalidUserOrPasswordException extends RuntimeException{
    public InvalidUserOrPasswordException( ) {
        super("Invalid email or password");
    }
}
