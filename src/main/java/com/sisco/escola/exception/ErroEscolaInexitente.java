package com.sisco.escola.exception;

public class ErroEscolaInexitente extends RuntimeException {
    ErroEscolaInexitente(String escolaInexistente){
        super(escolaInexistente);
    }
}
