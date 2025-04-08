package com.sisco.escola.exception;

public class ErroValidacaoException extends RuntimeException{
    public ErroValidacaoException(String erroValidacao){
        super(erroValidacao);
    }
}
