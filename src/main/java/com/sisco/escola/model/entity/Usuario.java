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
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuario", schema = "siscoescola")
public class Usuario{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 100, message = "O nome completo deve ter no máximo 100 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "O CPF é obrigatório")
    //@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$")
    //@CPF(message = "CPF inválido")
    @Size(min = 14) //nao precisa mas coloquei, o sistem é meu
    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 100, message = "O nome de usuário deve ter entre 3 e 100 100 caracteres")
    @Column(name = "nome_usuario", nullable = false, length = 100)
    private String usuario;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Formato de e-mail inválido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatório")
    @Size(min = 6, max = 255, message = "Senha inválida")
    @Column(name = "senha", nullable = false, unique = true, length = 255) // ver força de senha
    private String senha;

    @CreationTimestamp
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
