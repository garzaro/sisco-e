package com.sisco.escola.exception;

public class CodigoNotFoundException extends RuntimeException{
	
	public CodigoNotFoundException(String codigoInexitente) {
		super(codigoInexitente);
	}
}
