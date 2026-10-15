package com.sisco_e.escola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching
@SpringBootApplication
public class SiscoEApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiscoEApplication.class, args);
	}

}

/**
 * TODO-list
 * USUARIO
 * [x] Salvar usuario
 * [] Atualizar usuario
 * [] Desativar Usuario
 *
 * Escola
 * [x] Salvar escola
 * [] Atualizar escola
 * [] Desativar escola
 *
 * Diretor
 * [x] Salvar diretor
 * [] Atualizar diretor
 * [] Atualizar data inicio diretor
 * [] Atualizar data fim diretor
 * [] Desativar diretor
 * [] Reativar diretor
 *
 * Provedor
 * [x] Salvar Provedor
 * [] Atualizar provedor
 * [] Desativar provedor
 * [] Reativar provedor
 *
 * Contrato
 * [] Salvar Contrato
 * [] Atualizar contrato
 * [] Desativar contrato manualmente antes do vencimento
 * [] Desativar contrato automaticamente apos o termino
 *
 * Contrato
 * [] Aviso de corpo de requisicao malformado mas deve indicar o o campo - data contratação
 * [] Data a partir de hoje - vr conersao de data para o front depois
 *
 *
 * **/
