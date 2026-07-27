package com.sisco_e.escola.exception;

public class EmailAlreadyExistsException extends RegraNegocioException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
