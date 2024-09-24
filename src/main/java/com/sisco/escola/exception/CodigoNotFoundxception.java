package com.sisco.escola.exception;

public class CodigoNotFoundxception extends RuntimeException{
	
	public CodigoNotFoundxception(String codigoInexitente) {
		super(codigoInexitente);
	}
}
