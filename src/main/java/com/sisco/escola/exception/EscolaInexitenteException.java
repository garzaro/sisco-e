package com.sisco.escola.exception;

public class EscolaInexitenteException extends RuntimeException {
    public EscolaInexitenteException(String escolaInexistente){
        super(escolaInexistente);
    }
}
