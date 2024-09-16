package com.sisco.escola.exception;

public class CodigoJaCadastradoException extends RuntimeException{
    public CodigoJaCadastradoException(String erroCodigo){
        super(erroCodigo);
    }
}
