package com.utn.tesis.exception.types;

public class InvalidUserOrPasswordException extends RuntimeException{
    public InvalidUserOrPasswordException( ) {
        super("INVALID EMAIL OR PASSWORD");
    }
}
