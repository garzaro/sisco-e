package com.sisco_e.escola.model.entity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario",  schema = "escola")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private UUID id;

    @Column(name = "nome_completo", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
    private String nomeCompleto;
    	
    @Column(name = "usuario", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
    private String usuario;

    @Column(name = "cpf", unique = true, columnDefinition = "CHARACTER VARYING(14)")
	private String cpf;	
	
	@Column(name = "email", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(120)")
	private String email;
	
	@Column(name = "senha", nullable = false, columnDefinition = "CHARACTER VARYING(255)")
	private String senha;
	
	@CreationTimestamp
    @Column(nullable = false, updatable = false)
	private Instant dataCadastro;
	
	@Column(name = "ativo")
    private Boolean ativo;	

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "usuario_roles", schema = "escola", joinColumns = @JoinColumn(name = "usuario_id"))
	@Column(name = "role")
	@Builder.Default
	private Set<String> roles = new HashSet<>();

}
