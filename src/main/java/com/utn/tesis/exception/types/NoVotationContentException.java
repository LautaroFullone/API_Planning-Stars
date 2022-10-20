package com.utn.tesis.exception.types;

public class NoVotationContentException extends RuntimeException {
    public NoVotationContentException() {
        super("This User Story Do not have votations");
    }
}
