package com.utn.tesis.exception.types;

public class PartyNotFoundException extends RuntimeException {
    public PartyNotFoundException() {
        super("Party Not Found");
    }
}
