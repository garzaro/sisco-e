package com.sisco.escola.model.entity;
 /**
  * O que acontece aqui:
  *
  * @Size(max = 10) → valida na camada de bean validation (antes de chegar no banco), geralmente na sua camada de serviço/controller.
  *
  * @Column(length = 10) → impõe a restrição física no banco de dados (DDL), que será validada apenas no momento da persistência.
  * */

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "escola", schema = "siscoescola")
public class Escola {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "O nome da escola é obrigatório")
    @Size(max = 100, message = "O nome escola deve ter no máximo 100 caracteres")
    @Column(name = "nome_escola", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O código da escola é obrigatóro")
    @Size(max = 10, message = "O código escola deve ter no máximo 10 caracteres")
    @Column(name = "codigo_escola", nullable = false, length = 10)
    private String codigo;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255 , message = "O email deve ter no máximo 255 caracteres")
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @NotBlank(message = "O Estado é obrigatório")
    @Size(max = 25, message = "O Estado deve ter no máximo 25 caracteres")
    @Column(name = "estado", nullable = false, length = 25)
    private String estado;


    @NotBlank(message = "O município é obrigatório")
    @Size(max = 100, message = "O município deve ter no máximo 100 caracteres")
    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Rua, avenida, praça, bloco é obrigatório")
    @Size(max = 255, message = "O campo logradouro deve ter no máximo 255 caracteres")
    @Column(name = "logradouro", nullable = false, length = 255)
    private String logradouro;

    @CreationTimestamp
    @Column(name = "data_cadastro", updatable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDateTime dataCadastro;

    /*data*/
    /*@CreationTimestamp
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;*/
    
    /*@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;*/
    
    /*GETTERS AND SETTERS*/
}
