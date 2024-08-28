package com.sisco.escola.exception;

public class ErroEscolaInexitente extends RuntimeException {
    public ErroEscolaInexitente(String escolaInexistente){
        super(escolaInexistente);
    }
}
