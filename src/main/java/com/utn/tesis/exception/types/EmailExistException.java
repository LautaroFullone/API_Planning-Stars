package com.utn.tesis.exception.types;

public class EmailExistException extends RuntimeException  {
    public EmailExistException() {
        super("EMAIL in use.");
    }
}
