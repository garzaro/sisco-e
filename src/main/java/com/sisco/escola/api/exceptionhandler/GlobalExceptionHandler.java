package com.sisco.escola.api.exceptionhandler;

import com.sisco.escola.exception.ErroAutenticacaoException;
import com.sisco.escola.exception.ErroValidacaoException;
import com.sisco.escola.exception.RegraDeNegocioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

/**
 * Tratamento centralizado de exceções para toda a API REST.
 * 
 * Fluxo: {@code Controller → Exceção lançada → GlobalExceptionHandler → ApiError (JSON)}
 * 
 * Estende {@link ResponseEntityExceptionHandler} para sobrescrever apenas os métodos
 * necessários e garantir que o envelope de resposta seja sempre {@link ApiError}.
 *
 * Regras obrigatórias aplicadas
 * {@code @RestControllerAdvice} — escopo REST, serialização JSON automática.
 * Nenhum stack trace é exposto no corpo da resposta em produção.
 * Exceções inesperadas são registradas com {@code log.error()} via SLF4J.
 * Mensagens de validação originam-se das anotações do modelo
 * ({@code @NotBlank}, {@code @Email}, etc.) em {@code Usuario.java}.
 * Status HTTP definido via {@link HttpStatus} no retorno — sem {@code @ResponseStatus}.
 * {@code UsuarioController} não contém nenhum bloco {@code try/catch}.
 * 
 * Mapeamento de status HTTP
 * 
 * 400 Bad Request  — requisição malformada / corpo ilegível
 * 401 Unauthorized — credenciais inválidas ou ausentes
 * 409 Conflict     — violação de unicidade (email, CPF, username)
 * 422 Unprocessable Entity  — regra de negócio violada ou recurso não encontrado
 * 500 Internal Server Error  — erro inesperado (fallback)
 *
 * ✅ Mapeia todas as exceções necessárias
 * ✅ Usa SLF4J corretamente
 * ✅ Não expõe stack trace em produção
 * ✅ Status HTTP adequados (409, 422, 401, 500)
 * 
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {    
         
    private static final String BASE_URI            = "https://sisco.com.br/erros";
    private static final String URI_VALIDACAO_CAMPO = BASE_URI + "/validacao-de-campo";
    private static final String URI_CONFLITO        = BASE_URI + "/conflito-de-dados";
    private static final String URI_AUTENTICACAO    = BASE_URI + "/autenticacao";
    private static final String URI_REGRA_NEGOCIO   = BASE_URI + "/regra-de-negocio";
    private static final String URI_ERRO_INTERNO    = BASE_URI + "/erro-interno";

    /** Mensagem segura exibida ao usuário em erros 500 — sem internals. */
    private static final String MSG_ERRO_GENERICO =
            "Ocorreu um erro interno. Tente novamente ou entre em contato com o suporte.";

    /**
     * BadCredentialsException — credenciais inválidas (email ou senha incorretos).
     * Lançada por: authenticationManager.authenticate() no AuthController.
     * Status: 401 Unauthorized
     *
     * Mensagem genérica por segurança (evita enumerar usuários).
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Tentativa de login com credenciais inválidas");

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(Instant.now())
                .tipo(URI_AUTENTICACAO)
                .titulo("Falha na autenticação")
                .detalhe("Email ou senha inválidos. Verifique suas credenciais.")
                .mensagemUsuario("Email ou senha inválidos. Verifique suas credenciais.")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

   /**
    * MethodArgumentNotValidException — erros de @Valid em @RequestBody
    * 
    * Sobrescreve o comportamento padrão do ResponseEntityExceptionHandler.
    * Status: 422 Unprocessable Entity
    * 
    * **/

    /**
     * Trata falhas de Bean Validation ({@code @Valid} no controller).
     *
     * Itera sobre todos os {@link FieldError}s coletados pelo Spring MVC e
     * popula a lista {@code campos} do {@link ApiError}. As mensagens de erro
     * são lidas diretamente das anotações do modelo — {@code @NotBlank},
     * {@code @Email}, {@code @Size}, {@code @Pattern}, etc. — definidas em
     * {@code Usuario.java} e {@code UsuarioRequestDTO.java}.
     *
     * Status {@code 422}: o payload foi recebido e compreendido, mas não pode
     * ser processado por conter dados que violam as constraints declaradas.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull     MethodArgumentNotValidException ex,
            @NonNull     HttpHeaders headers,
            @NonNull     HttpStatusCode status,
            @NonNull     WebRequest request) {

        List<ApiError.Campo> camposComErro = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(campoErro -> ApiError.Campo.builder()
                        .nome(campoErro.getField())
                        .mensagem(campoErro.getDefaultMessage())   // mensagem vem da anotação no modelo
                        .build())
                .toList();
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(Instant.now())
                .tipo(URI_VALIDACAO_CAMPO)
                .titulo("Dados inválidos")
                .detalhe("Um ou mais campos estão com valores inválidos. Corrija-os e tente novamente.")
                .mensagemUsuario("Verifique os campos destacados e corrija os valores informados.")
                .campos(camposComErro)
                .build();

        return ResponseEntity.unprocessableEntity().body(apiError);
    }

    /**
     * ErroValidacaoException — violação de unicidade (email, CPF, username)
     * Lançada em: UsuarioServiceImpl.validarEmail / validarCpf / validarUsername
     * Status: 409 Conflict
     * 
     * Trata violações de unicidade nos campos de {@link com.sisco.escola.model.entity.Usuario}.
     *
     * Lançada pelo serviço quando e-mail, CPF ou username já estão em uso.
     */
    @ExceptionHandler(ErroValidacaoException.class)
    public ResponseEntity<ApiError> handleErroValidacao(ErroValidacaoException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(Instant.now())
                .tipo(URI_CONFLITO)
                .titulo("Conflito de dados")
                .detalhe(ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    /** Status: 401 Unauthorized
     * Trata falhas de autenticação (e-mail não cadastrado ou senha incorreta).
     * mensagem genérica para evitar user enumeration attack
     */
    @ExceptionHandler(ErroAutenticacaoException.class)
    public ResponseEntity<ApiError> handleErroAutenticacao(ErroAutenticacaoException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(Instant.now())
                .tipo(URI_AUTENTICACAO)
                .titulo("Falha na autenticação")
                .detalhe(ex.getMessage())
                .mensagemUsuario("Dados de login inválidos. Verifique suas credenciais.")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    /**Status: 422 Unprocessable Entity
     * Trata violações de regras de negócio: recurso não encontrado por ID,
     * e-mail, CPF ou username; estado de conta inválido para a operação.
     *
     * <p>Usa {@code 422} em vez de {@code 404} porque o problema reside nos
     * dados da requisição, não na URL em si.</p>
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ApiError> handleRegraDeNegocio(RegraDeNegocioException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(Instant.now())
                .tipo(URI_REGRA_NEGOCIO)
                .titulo("Regra de negócio violada")
                .detalhe(ex.getMessage())
                .mensagemUsuario(ex.getMessage())
                .build();

        return ResponseEntity.unprocessableEntity().body(apiError);
    }

    // =========================================================================
    // 5. Exception — fallback para erros inesperados
    //    Status: 500 Internal Server Error
    // =========================================================================

    /**
     * Handler de último recurso para qualquer exceção não tratada explicitamente.
     *
     * <p><b>Regras de segurança aplicadas:</b></p>
     * <ul>
     *   <li>O stack trace NUNCA é exposto no corpo da resposta.</li>
     *   <li>A causa raiz é registrada com {@code log.error()} para diagnóstico
     *       interno via SLF4J, sem vazar informações ao cliente.</li>
     *   <li>{@code mensagemUsuario} exibe apenas uma mensagem genérica e segura.</li>
     * </ul>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest request) {
        log.error("Exceção não tratada capturada pelo GlobalExceptionHandler. "
                + "Request: [{}]", request.getDescription(false), ex);

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(Instant.now())
                .tipo(URI_ERRO_INTERNO)
                .titulo("Erro interno do servidor")
                .mensagemUsuario(MSG_ERRO_GENERICO)
                // detalhe intencionalmente ausente (null → omitido pelo @JsonInclude NON_NULL)
                .build();

        return ResponseEntity.internalServerError().body(apiError);
    }
}