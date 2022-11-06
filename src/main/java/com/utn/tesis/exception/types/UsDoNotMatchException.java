package com.utn.tesis.exception.types;

public class UsDoNotMatchException extends RuntimeException {
    public UsDoNotMatchException() {
        super("UserStory doesn´t match");
    }

}