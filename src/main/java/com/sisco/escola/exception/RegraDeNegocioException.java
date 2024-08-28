package com.sisco.escola.exception;

public class RegraDeNegocioException extends RuntimeException{
    
    public RegraDeNegocioException(String escolaJaCadastrada) {
        
        super(escolaJaCadastrada);
    }
}
