package com.utn.tesis.exception.types;

public class EmailExistException extends RuntimeException  {
    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
