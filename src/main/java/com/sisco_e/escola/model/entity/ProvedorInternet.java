package com.sisco_e.escola.model.entity;

import java.util.UUID;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_provedor_net", schema = "siscoescola")
public class ProvedorInternet {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "uuid_provedor")
	private UUID uuid;

	@Column(name = "nome_provedor", nullable = false, columnDefinition = "CHARACTER VARYING(120)")
	private String nomeProvedor;

	@Column(name = "cnpj", nullable = false, columnDefinition = "CHARACTER VARYING(20)")
	private String cnpj;

	@Column(name = "telefone", nullable = false, columnDefinition = "CHARACTER VARYING(20)")
	private String telefone;

}
