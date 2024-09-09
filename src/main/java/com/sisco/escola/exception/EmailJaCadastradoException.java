package com.sisco.escola.exception;

public class EmailJaCadastradoException extends RuntimeException{
    EmailJaCadastradoException(String emailJaExiste){
        super(emailJaExiste);
    }
}
