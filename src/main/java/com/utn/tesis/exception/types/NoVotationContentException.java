package com.utn.tesis.exception.types;

public class NoVotationContentException extends RuntimeException {
    public NoVotationContentException() {
        super("None votation received");
    }
}
