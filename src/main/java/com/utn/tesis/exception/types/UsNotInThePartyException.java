package com.utn.tesis.exception.types;

public class UsNotInThePartyException extends RuntimeException {
    public UsNotInThePartyException() {
        super("UserStory not present in the party");
    }

}
