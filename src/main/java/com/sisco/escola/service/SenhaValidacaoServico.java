package com.sisco.escola.service;

import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.validacao.CaracteresDadosCustomizados;
import com.sisco.escola.validacao.RegraCaracteresRepetidos;
import org.passay.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SenhaValidacaoServico {

    private final PasswordValidator validator;

    public SenhaValidacaoServico(PasswordValidator validator){
        this.validator = validator;
    }

    public void validarSenha(String senha) {
        RuleResult result = validator.validate(new PasswordData(senha));
        if (!result.isValid()) {
            String mensagem = String.join(", ", validator.getMessages(result));
            throw new ErroValidacaoException(mensagem);
        }
    }
}
