package com.sisco.escola.exception;

public class EmailJaCadastrado extends RuntimeException{
    EmailJaCadastrado(String emailJaExiste){
        super(emailJaExiste);
    }
}
