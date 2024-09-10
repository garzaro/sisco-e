package com.sisco.escola.exception;

public class CpfJaCadastradoException extends RuntimeException{
    public CpfJaCadastradoException(String cpfCadastrado) {
        super(cpfCadastrado);
    }
}
