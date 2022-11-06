package com.utn.tesis.exception.types;

public class idNotMatchException extends RuntimeException{
    public idNotMatchException( ) {
        super("The ID doesn´t match");
    }
}
