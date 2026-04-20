package com.sisco.escola.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;

//Implementacao retirada de: https://www.devmedia.com.br/validacao-de-cpf-em-java/4110

//public class CpfValidador implements ConstraintValidator<CpfValido, String> {
//
//    @Override
//    public void initialize(CpfValido constraintAnnotation) {
//    }
//
//    @Override
//    public boolean isValid(String cpf, ConstraintValidatorContext context) {
//        if (cpf == null || cpf.trim().isEmpty()) {
//            return false;
//        }
//
//        cpf = cpf.replaceAll("\\D", "");
//
//        if (cpf.length() != 11) {
//            return false;
//        }
//
//        // Verifica CPFs com números iguais (ex: 111.111.111-11)
//        if (cpf.matches("(\\d)\\1{10}")) {
//            return false;
//        }
//
//        try {
//            char dig10, dig11;
//            int sm, i, r, num, peso;
//
//            // Calculo do 1o. Digito Verificador
//            sm = 0;
//            peso = 10;
//            for (i = 0; i < 9; i++) {
//                num = (int) (cpf.charAt(i) - 48);
//                sm = sm + (num * peso);
//                peso = peso - 1;
//            }
//
//            r = 11 - (sm % 11);
//            if ((r == 10) || (r == 11)) {
//                dig10 = '0';
//            } else {
//                dig10 = (char) (r + 48);
//            }
//
//            // Calculo do 2o. Digito Verificador
//            sm = 0;
//            peso = 11;
//            for (i = 0; i < 10; i++) {
//                num = (int) (cpf.charAt(i) - 48);
//                sm = sm + (num * peso);
//                peso = peso - 1;
//            }
//
//            r = 11 - (sm % 11);
//            if ((r == 10) || (r == 11)) {
//                dig11 = '0';
//            } else {
//                dig11 = (char) (r + 48);
//            }
//
//            // Verifica se os digitos calculados conferem com os digitos informados.
//            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
//        } catch (InputMismatchException erro) {
//            return false;
//        }
//    }
//}

public class CpfValidador implements ConstraintValidator<CpfValido, String> {

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

