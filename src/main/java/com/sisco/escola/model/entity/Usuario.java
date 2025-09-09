package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * To-do list
 *  CAMPO SENHA
 *  []  Pelo menos uma letra minúscula ((?=.*[a-z])).
 *  []  Pelo menos uma letra maiúscula ((?=.*[A-Z])).
 *  []  Pelo menos um dígito ((?=.*\d)).
 *  []  Pelo menos um caractere especial ((?=.*[@$!%*?&])).
 *  []  Comprimento mínimo de 8 caracteres ({8,}).
 *  * */


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuario", schema = "siscoescola")
public class Usuario {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "NOme deve ter no máximo 100 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CPF é obrigatótio")
    @Size(max = 14)
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$")
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(max = 50, message = "Nome de usuario deve ter no máximo 50 caracteres")
    @Column(name = "nome_usuario", nullable = false, length = 50)
    private String usuario;

    @NotBlank(message = "Email  é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    @Size(min = 8, max = 32, message = "A senha deve ter entre 8 e 32 caracteres." )
    @Pattern(

            regexp = "^(?=.*[a-z])(?=.*[A-Z])" +
                    "(?=.*\\d)(?=.*[@$!%*?&])" +
                    "[A-Za-z\\d@$!%*?&]{8,}$",

            message = "A senha deve conter pelo" +
                    " menos uma letra maiúscula," +
                    " uma minúscula, um número e um" +
                    " caractere especial."
    )
    @Column(name = "senha")
    private String senha;

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

    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
