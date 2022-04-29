package com.utn.tesis.exception.types;

public class UsNotFoundException extends RuntimeException {
    public UsNotFoundException() {
        super("UserStory Not Found");
    }

}
