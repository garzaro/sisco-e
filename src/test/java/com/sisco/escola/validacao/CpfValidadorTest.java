package com.sisco.escola.validacao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * O que esses testes cobrem
 *
 * [x] Casos nulos e vazios
 * [x] Tamanho incorreto
 * [x] CPFs com dígitos repetidos
 * [x] CPF válido conhecido
 * [x] CPF com caracteres invalidos
 * [x] CPF inválido com dígito verificador errado
 *
 * */

@SpringBootTest
public class CpfValidadorTest {

    private  final CpfValidador cpfValidador = new CpfValidador();

    @Test
    @DisplayName("Falso para CPF nulo!")
    public void testDeveRetornarFalsoQuandoCpfNulo() {
        assertFalse(cpfValidador.isValid(null, null));
    }

    @Test
    @DisplayName("Falso para CPF vazio!")
    public void testDeveRetornarFalsoQuandoCpfVazio() {
        assertFalse(cpfValidador.isValid("", null));
    }

    @Test
    @DisplayName("Falso para CPF com tamanho invalido!")
    public void testDeveRetornarFalsoQuandoCpfComTamanhoInvalido() {
        assertFalse(cpfValidador.isValid("1234567890", null));
    }

    @Test
    @DisplayName("Falso para CPF com caracteres invalidos!")
    public void testDeveRetornarFalsoQuandoCpfComCaracteresInvalidos() {
        assertFalse(cpfValidador.isValid("abc", null));
    }

    @Test
    @DisplayName("Falso para CPF com digitos iguais!")
    public void testDeveRetornarFalsoQuandoCpfComDigitosIguais() {
        assertFalse(cpfValidador.isValid("11111111111", null));
    }

    @Test
    @DisplayName("Verdadeiro  para CPF valido!")
    public void testDeveRetornarTrueQuandoCpfValido() {
        assertTrue(cpfValidador.isValid("24800126894", null)); //sem mascara
//        assertTrue(cpfValidador.isValid("24.8001.368-94", null)); //com mascara
    }

    @Test
    @DisplayName("False para CPF invalido!")
    public void testDeveRetornarFalseQuandoCpfInvalido() {
        assertFalse(cpfValidador.isValid("12345678901", null)); //sem mascara
        assertFalse(cpfValidador.isValid("123.456.789-80", null)); //com mascara
    }

}
