package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.sisco.escola.validacao.CpfValido;

import java.time.Instant;

/**
 * To-do list
 * [] Muito acoplado com a base
 * 
 *  * */


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tab_usuario", schema = "siscoescola")
public class Usuario {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{usuario.nome.notblank}")
    @Size(max = 100, message = "{usuario.nome.size}")
    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "{usuario.cpf.notblank}")
    @Size(max = 14)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "{usuario.cpf.pattern}")
    @CpfValido
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "{usuario.username.notblank}")
    @Size(max = 50, message = "{usuario.username.size}")
    @Column(name = "nome_usuario", nullable = false, length = 50)
    private String usuario;

    @NotBlank(message = "{usuario.email.notblank}")
    @Email(message = "{usuario.email.valid}")
    @Size(max = 150, message = "{usuario.email.size}")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    @Size(min = 8, max = 32, message = "{usuario.senha.size}")
    //@Pattern() //remover
    /*
    [] Pelo menos uma letra minúscula ((?=.*[a-z])).
    [] Pelo menos uma letra maiúscula ((?=.*[A-Z])).
    [] Pelo menos um dígito ((?=.*\d)).
    [] Pelo menos um caractere especial ((?=.*[@$!%*?&])).
    [] Comprimento mínimo de 6 caracteres ({8,}).
    */
    @Pattern(

            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{6,}$",

            message = "{usuario.senha.pattern}"
    )
    @Column(name = "senha")
    private String password;

    /**
     * LEGADO
     * @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
     * ------------------------------------------------------------------
     * @CreationTimestamp
     * O valor é definido automaticamente pelo Hibernate na primeira vez que a entidade é salva.
     * Registra o momento EXATO da criação da entidade.
     * `updatable = false` garante que este campo nunca seja alterado após a criação.
     * */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant dataCadastro;

    @Column(name = "ativo")
    private Boolean ativo;

    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
