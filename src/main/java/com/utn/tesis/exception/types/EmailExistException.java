package com.utn.tesis.exception.types;

public class EmailExistException extends RuntimeException  {
    public EmailExistException() {
        super("Email in use");
    }
}
