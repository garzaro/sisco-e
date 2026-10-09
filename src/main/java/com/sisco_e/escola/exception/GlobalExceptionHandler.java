package com.sisco_e.escola.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**este cara captura as excessões**/

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private ResponseEntity<ErrorResponse> buildErrorResponse(String messageKey, HttpStatus status) {
        String mensagem = messageSource.getMessage(
                messageKey,
                null,
                messageKey,
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).body(new ErrorResponse(mensagem));
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErrorResponse> tratar(RegraNegocioException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        log.warn("Tentativa de login falha: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCpfAlreadyExistsException(CpfAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /** JSON malformado, corpo vazio ou tipo de campo inválido -> 400 em vez de 500. **/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Corpo da requisição ilegível: {}", ex.getMessage());
        return buildErrorResponse("erro.requisicao.corpo.invalido", HttpStatus.BAD_REQUEST);
    }

    /** Content-Type não suportado (ex.: text/plain no login) -> 415 em vez de 500. **/
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.warn("Tipo de conteúdo não suportado: {}", ex.getContentType());
        return buildErrorResponse("erro.requisicao.tipo.conteudo.nao.suportado", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /** Método HTTP não suportado (ex.: GET /sign-in) -> 405 em vez de 500. **/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Método HTTP não suportado: {}", ex.getMethod());
        return buildErrorResponse("erro.requisicao.metodo.nao.suportado", HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Falha de infraestrutura (Redis fora do ar - RedisConnectionFailureException -
     * ou banco inacessível) -> 503 em vez de 500, indicando que é problema temporário do servidor.
     * DataAccessResourceFailureException é a superclasse de ambos os casos.
     **/
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessResourceFailure(DataAccessResourceFailureException ex) {
        log.error("Falha de acesso a recurso de dados (Redis/banco): {}", ex.getMessage(), ex);
        return buildErrorResponse("erro.servico.indisponivel", HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Tempo esgotado ao acessar recurso de dados (ex.: Redis caiu com a conexão já aberta ->
     * RedisCommandTimeoutException -> QueryTimeoutException) -> 503 em vez de 500.
     **/
    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleQueryTimeout(QueryTimeoutException ex) {
        log.error("Tempo esgotado ao acessar recurso de dados (Redis/banco): {}", ex.getMessage());
        return buildErrorResponse("erro.servico.indisponivel", HttpStatus.SERVICE_UNAVAILABLE);
    }

    /** Handler de exceções genérico para quaisquer outras exceções inesperadas. **/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Ocorreu um erro interno no servidor: {}", ex.getMessage(), ex);
        return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor!");
    }        
}
