package com.utn.tesis.exception.types;

public class UserNotFindException extends RuntimeException{
    public UserNotFindException() {
        super("User Not Find");
    }
}
