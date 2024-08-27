package com.sisco.escola.exception;

public class ErroCadastroInexistente extends RuntimeException{
	
	public ErroCadastroInexistente(String inepInexistente) {
		super(inepInexistente);		
	}

}
