package com.sisco_e.escola.api.dto.request;

import java.time.Instant;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiretorRequestDTO {

	@NotBlank(message = "{diretor.nome.notblank}")
	@Size(max = 120, message = "{diretor.nome.size}")
	private String nomeDiretor;

	@NotBlank(message = "{diretor.cpf.notblank}")
	@CPF(message = "{diretor.cpf.invalido}")
	@Size(max = 11, message = "{diretor.cpf.size}")
	private String cpf;

	@NotBlank(message = "{diretor.matricula.notblank}")
	@Size(min = 6, max = 19, message = "{diretor.matricula.size}")
	private String matriculaFuncional;

	@NotBlank(message = "{diretor.email.notblank}")
	@Email(message = "{diretor.email.valido}")
	@Size(max = 120, message = "{diretor.email.size}")
	private String email;

	@Email(message = "{diretor.email.pessoal.valido}")
	@Size(max = 120, message = "{diretor.email.pessoal.size}")
	private String emailPessoal;

	@NotNull(message = "{diretor.data.posse}")
	private Instant dataPosse;

	private Instant dataFimMandato;	
}
