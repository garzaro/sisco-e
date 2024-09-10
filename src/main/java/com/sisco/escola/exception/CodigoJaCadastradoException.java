package com.sisco.escola.exception;

public class CodigoJaCadastradoException extends RuntimeException{
    CodigoJaCadastradoException(String mensagemDeErro){
        super(mensagemDeErro);
    }
}
