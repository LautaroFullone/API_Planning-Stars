package com.utn.tesis.exception.types;

public class usNameRepetedException extends RuntimeException {
    public usNameRepetedException() {
        super("UserStory name is in USE.");
    }

}
