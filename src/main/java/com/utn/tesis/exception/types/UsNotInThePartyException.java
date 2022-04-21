package com.utn.tesis.exception.types;

public class UsNotInThePartyException extends RuntimeException {
    public UsNotInThePartyException() {
        super("UserStory Not Found in the Party");
    }

}
