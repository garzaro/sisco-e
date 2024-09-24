package com.sisco.escola.exception;

public class EscolaNotFoundException extends RuntimeException {
    public EscolaNotFoundException(String escolaInexistente){
        super(escolaInexistente);
    }
}
