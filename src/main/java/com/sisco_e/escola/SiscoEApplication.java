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
 *
 * DTO
 * [] Separar entre dtorequest e dtoresponse
 *
 * USUARIO
 * [x] Salvar usuario - dividido dto rq re
 * [] Atualizar usuario
 * [] Desativar Usuario
 *
 * Escola
 * [x] Salvar escola
 * [] Atualizar escola
 * [] Desativar escola
 * [] Deve validar contrato duplicado com o mesmo provedor
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
 * JWT
 * [] Nao retornar o refresh ap cliente no corpo e sim no cookie
 * **/


//O Fluxo Ideal de Dados
//
//		O padrão arquitetural recomendado (como na Clean Architecture ou Hexagonal Architecture) dita que a transformação ocorra o quanto antes:
//		[Cliente] -> (JSON) -> [Controller / Handler]
//		│
//		▼ (Valida e mapeia DTO -> Modelo/Parâmetros)
//		[Camada de Serviço] (Regra de negócio pura)
//		│
//		▼ (Persistência)
//		[Repository / DB]
