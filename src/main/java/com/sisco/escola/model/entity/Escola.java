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
    private String nome;
    
    @Column(name = "codigo_escola")
    private String codigo;
    
    @Column(name = "cidade_escola")
    private String cidade;
    
    @Column(name = "bairro_escola")
    private String bairro;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name ="telefone")
    private String telefone;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    
    /*GETTERS AND SETTERS*/
    /*HASHCODE AND EQUALS*/
    /*TO STRING*/
}
