package com.sisco.escola.exception;

public class ErroDeAutenticacao extends RuntimeException {
    public ErroDeAutenticacao(String mensagemErroDeAutenticacao) {
        super(mensagemErroDeAutenticacao);
    }
}
