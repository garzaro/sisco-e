package com.sisco.escola.exception;

public class EscolaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public EscolaNotFoundException(String escolaInexistente){
        super(escolaInexistente);
    }
}
