package com.sisco.escola.exception;

public class ErroEscolaJaCadastrada extends RuntimeException {

	public ErroEscolaJaCadastrada(String escolaJaExiste) {
		super(escolaJaExiste);
		
	}

}
