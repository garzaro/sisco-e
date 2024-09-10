package com.sisco.escola.exception;

public class ErroEscolaInexitenteException extends RuntimeException {
    public ErroEscolaInexitenteException(String escolaInexistente){
        super(escolaInexistente);
    }
}
