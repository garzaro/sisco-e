package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;


import java.time.Instant;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "siscoescola")
public class Usuario {
    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "uuid")
    private UUID uuid;
   
    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nomeCompleto;
       
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "usuario", nullable = false, length = 50)
    private String usuario;
    
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
   
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

    @Column(name = "ativo")
    private Boolean ativo;

    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
