package com.sisco.escola.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidador  implements ConstraintValidator<CpfValido, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.trim().isEmpty()) return false;

        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\\1{10}")) return false;

        int digito1 = calculaDigito(cpf, 9, 10);
        int digito2 = calculaDigito(cpf, 10, 11);

        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    private int calculaDigito(String cpf, int tamanho, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < tamanho; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (pesoInicial - i);
        }
        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
    }
}
