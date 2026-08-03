package com.sisco_e.escola.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @AfterEach
    void clearLocaleContext() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void shouldResolveMessageByKeyForBusinessException() {
        Locale locale = Locale.forLanguageTag("pt-BR");
        LocaleContextHolder.setLocale(locale);

        when(messageSource.getMessage(eq("dados.duplicado.nao.permitido"), eq(null),
                eq("dados.duplicado.nao.permitido"), eq(locale)))
                        .thenReturn("Dados duplicados não permitido");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

        ResponseEntity<ErrorResponse> response = handler.tratar(
                new RegraNegocioException("dados.duplicado.nao.permitido"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados duplicados não permitido", response.getBody().getMessage());
    }

    @Test
    void shouldFallbackToLiteralMessageWhenNoKeyExists() {
        Locale locale = Locale.forLanguageTag("pt-BR");
        LocaleContextHolder.setLocale(locale);

        when(messageSource.getMessage(eq("Dados duplicados não permitido"), eq(null),
                eq("Dados duplicados não permitido"), eq(locale)))
                        .thenReturn("Dados duplicados não permitido");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

        ResponseEntity<ErrorResponse> response = handler.tratar(
                new RegraNegocioException("Dados duplicados não permitido"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados duplicados não permitido", response.getBody().getMessage());
    }
}
