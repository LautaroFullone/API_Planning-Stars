package com.utn.tesis.exception.types;

public class idNotMatchException extends RuntimeException{
    public idNotMatchException( ) {
        super("The Id Do not Match");
    }
}
