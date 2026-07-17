package com.sisco.escola.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Envelope padronizado de resposta de erro para a API REST.
 *
 * Campos nulos são omitidos automaticamente na serialização JSON via
 * {@link JsonInclude.Include#NON_NULL}, mantendo o payload limpo conforme o
 * tipo de erro retornado.
 * 
 * Campos de {@link com.sisco.escola.model.entity.Usuario} representados:
 * 
 * id      – identificador do recurso com problema (quando aplicável)
 * nome    – nome do usuário referenciado no contexto do erro
 * cpf     – CPF do usuário referenciado no contexto do erro
 * usuario – username (nome_usuario) referenciado no contexto do erro
 * email   – e-mail do usuário referenciado no contexto do erro
 * 
 * senha e dataCadastro são intencionalmente omitidos por segurança e irrelevância.
 * 
 * Fluxo: Controller → Exceção → GlobalExceptionHandler → ApiError (JSON)
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    /**
     * Código HTTP numérico do erro (ex.: 400, 401, 404, 422, 500).
     */
    private Integer status;

    /**
     * Momento em que o erro ocorreu (UTC), no formato ISO-8601.
     * Preenchido automaticamente pelo handler.
     */
    private Instant timestamp;

    /**
     * Tipo do erro — URI semântica que identifica a categoria do problema.
     * Exemplo: "https://sisco.com.br/erros/validacao-de-campo"
     */
    private String tipo;

    /**
     * Título curto e legível do erro.
     * Exemplo: "Dados inválidos", "Recurso não encontrado".
     */
    private String titulo;

    /**
     * Mensagem detalhada destinada ao consumidor da API.
     * Deve ser informativa o suficiente para diagnóstico sem expor internals.
     */
    private String detalhe;

    /**
     * Mensagem de orientação ao usuário final (não-técnica).
     * Presente apenas quando o handler julgar necessário guiar o cliente.
     */
    private String mensagemUsuario;

    /**contexto opcional do Usuário referenciado no erro**/

    /** ID do usuário envolvido no erro (quando rastreável). */
    private UUID uuid; //usuarioId;

    /** Nome completo do usuário envolvido no erro. */
    private String nomeCompleto;

    /** CPF do usuário envolvido no erro. */
    private String cpf;

    /** Username (nome_usuario) do usuário envolvido no erro. */
    private String usuario;

    /** E-mail do usuário envolvido no erro. */
    private String email;

    /** Status ativo/inativo do usuário referenciado, quando relevante. */
    private Boolean ativo;

    /**erros de campo — populados em falhas de Bean Validation (@Valid)**/
    /**
     * Lista de erros por campo, gerada quando a validação de entrada falha
     * (ex.: {@code MethodArgumentNotValidException}).
     * Será {@code null} — e portanto omitido — nos demais tipos de erro.
     */
    private List<Campo> campos;

    /**classe interna: Campo**/
    /**
     * Representa um erro de validação associado a um campo específico do payload.
     *
     * <p>Exemplo de serialização:</p>
     * <pre>
     * {
     *   "nome": "email",
     *   "mensagem": "deve ser um endereço de e-mail bem formado"
     * }
     * </pre>
     */
    @Builder
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Campo {

        /**
         * Nome do campo que falhou na validação.
         * Corresponde ao nome do atributo Java (ex.: "email", "cpf", "nome").
         */
        private String nome;

        /**
         * Mensagem descritiva da violação de constraint para este campo.
         * Originada das anotações Bean Validation ({@code @NotBlank}, {@code @Email}, etc.).
         */
        private String mensagem;
    }
}
