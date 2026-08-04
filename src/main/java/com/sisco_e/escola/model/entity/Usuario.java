package com.sisco_e.escola.model.entity;

import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_usuario",  schema = "siscoescola")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private UUID uuid;

    @Column(name = "nome_completo", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
    private String nomeCompleto;
    	
    @Column(name = "username", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
    private String username;

    @Column(name = "cpf", unique = true, columnDefinition = "CHARACTER VARYING(11)")
	private String cpf;	
	
	@Column(name = "email", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(120)")
	private String email;
	
	@Column(name = "password", nullable = false, columnDefinition = "CHARACTER VARYING(255)")
	private String password;
	
	@CreationTimestamp
    @Column(nullable = false, updatable = false)
	private Instant dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, updatable = false)
	private Instant dataAtualizacao;

	@Column(name = "is_ativo")
    private Boolean isAtivo;

//	@ElementCollection(fetch = FetchType.EAGER)
//	@CollectionTable(name = "usuario_roles", schema = "escola", joinColumns = @JoinColumn(name = "usuario_id"))
//	@Column(name = "role")
//	@Builder.Default
//	private Set<String> roles = new HashSet<>();
	

}
