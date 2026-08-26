package com.sisco_e.escola.model.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sisco_e.escola.model.enums.TipoLink;

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
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_link_internet", schema = "siscoescola", uniqueConstraints = @UniqueConstraint(name = "uk_link_internet_contrato_net", columnNames = "uuid_contrato_net"))
public class LinkInternet {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "uuid_link_internet")
	private UUID uuid;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_link", nullable = false, columnDefinition = "CHARACTER VARYING(30)")
	private TipoLink tipoLink;

	@Column(name = "ip_publico", nullable = false, unique = true, columnDefinition = "CHARACTER VARYING(45)")
	private String ipPublico;

	@Column(name = "mascara_rede", nullable = false, columnDefinition = "CHARACTER VARYING(45)")
	private String mascaraRede;

	@Column(name = "gateway", nullable = false, columnDefinition = "CHARACTER VARYING(45)")
	private String gateway;

	@Column(name = "dns_primario", nullable = false, columnDefinition = "CHARACTER VARYING(45)")
	private String dnsPrimario;

	@Column(name = "dns_secundario", columnDefinition = "CHARACTER VARYING(45)")
	private String dnsSecundario;

	@Column(name = "vlan_id")
	private Integer vlanId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid_provedor", referencedColumnName = "uuid_provedor", nullable = false)
	private ProvedorInternet provedorInternet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid_escola", referencedColumnName = "uuid_escola", nullable = false)
	private Escola escola;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid_contrato_net", referencedColumnName = "uuid_contrato_net", nullable = false, unique = true)
	private ContratoInternet contratoInternet;

	@Column(name = "is_ativo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean isAtivo;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	private Instant dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private Instant dataAtualizacao;

}
