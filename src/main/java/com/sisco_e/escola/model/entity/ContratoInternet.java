package com.sisco_e.escola.model.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sisco_e.escola.model.enums.StatusContrato;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tab_contrato_net", schema = "siscoescola",
	uniqueConstraints = @UniqueConstraint(name = "uk_contratos_internet_escola_provedor_data",
		columnNames = { "uuid_escola", "uuid_provedor", "data_contratacao" }))
public class ContratoInternet {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "uuid_contrato_net")
	private UUID uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid_escola", nullable = false)
	private Escola escola;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid_provedor", nullable = false)
	private ProvedorInternet provedor;

	@Column(name = "data_contratacao", nullable = false)
	private LocalDate dataContratacao;

    @Column(name = "data_fim_contrato")
	private LocalDate dataFimContrato;

    @Column(name = "velocidade", nullable = false)
	private String velocidade;

	@Column(name = "valor_mensal", nullable = false, columnDefinition = "NUMERIC(10,2)")
	private BigDecimal valorMensal;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, columnDefinition = "CHARACTER VARYING(20)")
	private StatusContrato status;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	private Instant dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private Instant dataAtualizacao;
}
