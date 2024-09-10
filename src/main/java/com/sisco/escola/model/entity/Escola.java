package com.sisco.escola.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "escola", schema = "siscoescola")
public class Escola {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nome_escola")
    private String nomeEscola;
    
    @Column(name = "inep")
    private String cadastroEscola;    
    
    @Column(name = "cidade_escola")
    private String cidadeEscola;
    
    @Column(name = "bairro_escola")
    private String bairroEscola;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name ="telefone")
    private Integer telefone;
    
    
    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
