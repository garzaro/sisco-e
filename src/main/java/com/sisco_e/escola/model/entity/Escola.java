package com.sisco_e.escola.model.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_escola", schema = "siscoescola")
public class Escola {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_escola")
	private UUID uuid;

	@Column(name = "nome_escola", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
	private String nomeEscola;

    @Column(name = "codigo_escola", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(120)")
	private String codigoEscola;

	@Column(name = "municipio", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
	private String municipio;

	@Column(name = "estado", nullable = false, columnDefinition = "CHARACTER VARYING(2)")
	private String estado;	

    @Column(name = "cep", columnDefinition = "CHARACTER VARYING(8)")
	private String cep;

    @Column(name = "logradouro", columnDefinition = "CHARACTER VARYING(255)")
	private String logradouro;

    @Column(name = "bairro", columnDefinition = "CHARACTER VARYING(255)")
	private String bairro;

	@OneToOne(mappedBy = "escola", fetch = FetchType.LAZY)
	private Diretor diretor;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_escola", nullable = false, columnDefinition = "CHARACTER VARYING(30)")
	private TipoEscola tipoEscola;

    @Column(name = "is_ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAtivo;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
	private Instant dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false)
	private Instant dataAtualizacao;

	
	
}
