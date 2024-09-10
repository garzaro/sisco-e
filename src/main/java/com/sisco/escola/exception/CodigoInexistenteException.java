package com.sisco.escola.exception;

public class CodigoInexistenteException extends RuntimeException{
	
	public CodigoInexistenteException(String codigoInexitente) {
		super(codigoInexitente);
	}
}
