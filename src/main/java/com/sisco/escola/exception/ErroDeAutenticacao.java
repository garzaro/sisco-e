package com.sisco.escola.exception;

public class ErroDeAutenticacao extends RuntimeException {
    public ErroDeAutenticacao(String erroAutenticacao) {
        super(erroAutenticacao);
    }
}
