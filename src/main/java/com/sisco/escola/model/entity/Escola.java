package com.sisco.escola.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

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
    
    @Column(name = "nome_escola")
    private String nome;
    
    @Column(name = "codigo_escola")
    private String codigo;

    @Column(name = "email")
    private String email;

    @Column(name = "estado")
    private String estado;
    
    @Column(name = "municipio")
    private String municipio;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "logradouro")
    private String logradouro;

    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    
    /*@ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;*/
    
    /*GETTERS AND SETTERS*/
}
