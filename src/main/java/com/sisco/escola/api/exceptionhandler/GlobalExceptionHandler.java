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
 * <p>Fluxo: {@code Controller → Exceção lançada → GlobalExceptionHandler → ApiError (JSON)}</p>
 *
 * <p>Estende {@link ResponseEntityExceptionHandler} para sobrescrever apenas os métodos
 * necessários e garantir que o envelope de resposta seja sempre {@link ApiError}.</p>
 *
 * <h2>Regras obrigatórias aplicadas</h2>
 * <ul>
 *   <li>{@code @RestControllerAdvice} — escopo REST, serialização JSON automática.</li>
 *   <li>Nenhum stack trace é exposto no corpo da resposta em produção.</li>
 *   <li>Exceções inesperadas são registradas com {@code log.error()} via SLF4J.</li>
 *   <li>Mensagens de validação originam-se das anotações do modelo
 *       ({@code @NotBlank}, {@code @Email}, etc.) em {@code Usuario.java}.</li>
 *   <li>Status HTTP definido via {@link HttpStatus} no retorno — sem {@code @ResponseStatus}.</li>
 *   <li>{@code UsuarioController} não contém nenhum bloco {@code try/catch}.</li>
 * </ul>
 *
 * <h2>Mapeamento de status HTTP</h2>
 * <ul>
 *   <li><b>400 Bad Request</b>            — requisição malformada / corpo ilegível</li>
 *   <li><b>401 Unauthorized</b>           — credenciais inválidas ou ausentes</li>
 *   <li><b>409 Conflict</b>               — violação de unicidade (email, CPF, username)</li>
 *   <li><b>422 Unprocessable Entity</b>   — regra de negócio violada ou recurso não encontrado</li>
 *   <li><b>500 Internal Server Error</b>  — erro inesperado (fallback)</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // -------------------------------------------------------------------------
    // Constantes — URIs semânticas conforme RFC 7807 (Problem Details for HTTP APIs)
    // -------------------------------------------------------------------------

    private static final String BASE_URI            = "https://sisco.com.br/erros";
    private static final String URI_VALIDACAO_CAMPO = BASE_URI + "/validacao-de-campo";
    private static final String URI_CONFLITO        = BASE_URI + "/conflito-de-dados";
    private static final String URI_AUTENTICACAO    = BASE_URI + "/autenticacao";
    private static final String URI_REGRA_NEGOCIO   = BASE_URI + "/regra-de-negocio";
    private static final String URI_ERRO_INTERNO    = BASE_URI + "/erro-interno";

    /** Mensagem segura exibida ao usuário em erros 500 — sem internals. */
    private static final String MSG_ERRO_GENERICO =
            "Ocorreu um erro interno. Tente novamente ou entre em contato com o suporte.";

    // =========================================================================
    // 1. MethodArgumentNotValidException — erros de @Valid em @RequestBody
    //    Sobrescreve o comportamento padrão do ResponseEntityExceptionHandler.
    //    Status: 422 Unprocessable Entity
    // =========================================================================

    /**
     * Trata falhas de Bean Validation ({@code @Valid} no controller).
     *
     * <p>Itera sobre todos os {@link FieldError}s coletados pelo Spring MVC e
     * popula a lista {@code campos} do {@link ApiError}. As mensagens de erro
     * são lidas diretamente das anotações do modelo — {@code @NotBlank},
     * {@code @Email}, {@code @Size}, {@code @Pattern}, etc. — definidas em
     * {@code Usuario.java} e {@code UsuarioDTO.java}.</p>
     *
     * <p>Status {@code 422}: o payload foi recebido e compreendido, mas não pode
     * ser processado por conter dados que violam as constraints declaradas.</p>
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
                .map(fe -> ApiError.Campo.builder()
                        .nome(fe.getField())
                        .mensagem(fe.getDefaultMessage())   // mensagem vem da anotação no modelo
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

    // =========================================================================
    // 2. ErroValidacaoException — violação de unicidade (email, CPF, username)
    //    Lançada em: UsuarioServiceImpl.validarEmail / validarCpf / validarUsername
    //    Status: 409 Conflict
    // =========================================================================

    /**
     * Trata violações de unicidade nos campos de {@link com.sisco.escola.model.entity.Usuario}.
     *
     * <p>Lançada pelo serviço quando e-mail, CPF ou username já estão em uso.</p>
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

    // =========================================================================
    // 3. ErroAutenticacaoException — credenciais inválidas
    //    Lançada em: UsuarioServiceImpl.autenticar
    //    Status: 401 Unauthorized
    // =========================================================================

    /**
     * Trata falhas de autenticação (e-mail não cadastrado ou senha incorreta).
     *
     * <p>A mensagem ao usuário é propositalmente genérica para evitar a enumeração
     * de usuários (user enumeration attack).</p>
     */
    @ExceptionHandler(ErroAutenticacaoException.class)
    public ResponseEntity<ApiError> handleErroAutenticacao(ErroAutenticacaoException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(Instant.now())
                .tipo(URI_AUTENTICACAO)
                .titulo("Falha na autenticação")
                .detalhe(ex.getMessage())
                .mensagemUsuario("E-mail ou senha inválidos. Verifique suas credenciais.")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    // =========================================================================
    // 4. RegraDeNegocioException — recurso não encontrado / estado inválido
    //    Lançada em: UsuarioServiceImpl.encontrarOuLancarErro, buscarPorEmail, etc.
    //    Status: 422 Unprocessable Entity
    // =========================================================================

    /**
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