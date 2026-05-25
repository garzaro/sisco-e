package com.sisco.escola.exception;

public class ErroAutenticacaoException extends RuntimeException {
    public ErroAutenticacaoException(String erroAutenticacao) {
        super(erroAutenticacao);
    }
}
