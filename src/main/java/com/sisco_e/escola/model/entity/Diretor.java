package com.sisco_e.escola.model.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "tb_diretor", schema = "siscoescola")
public class Diretor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "uuid")
	private UUID uuid;

	@Column(name = "nome_diretor", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
	private String nomeDiretor;

	@Column(name = "cpf", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(11)")
	private String cpfDiretor;

	@Column(name = "matricula_funcional", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(20)")
	private String matriculaFuncional;

	@Column(name = "email", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(120)")
	private String emailCorporativo;

	@Column(name = "email_pessoal", columnDefinition = "CHARACTER VARYING(120)")
	private String emailPessoal;
	
	@Column(name = "data_posse", nullable = false, updatable = false)
	private Instant dataPosse;

	@Column(name = "data_fim_mandato")
	private Instant dataFimMandato;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "escola_uuid", nullable = false, unique = true)
	private Escola escola;

	@Column(name = "is_ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isAtivo;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false)
	private Instant dataAtualizacao;	

}
