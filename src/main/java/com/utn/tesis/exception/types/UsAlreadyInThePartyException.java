package com.utn.tesis.exception.types;

public class UsAlreadyInThePartyException extends RuntimeException {
    public UsAlreadyInThePartyException() {
        super("UserStory already in the Party");
    }

}

