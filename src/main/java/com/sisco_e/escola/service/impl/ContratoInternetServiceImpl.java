package com.sisco_e.escola.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.DiretorDTO;
import com.sisco_e.escola.api.dto.EscolaDTO;
import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.model.entity.Diretor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.ContratoInternetDTO;
import com.sisco_e.escola.mapper.ContratoInternetMapper;
import com.sisco_e.escola.model.entity.ContratoInternet;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.entity.ProvedorInternet;
import com.sisco_e.escola.model.enums.StatusContrato;
import com.sisco_e.escola.model.repository.ContratoInternetRepository;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.model.repository.ProvedorInternetRepository;
import com.sisco_e.escola.service.ContratoInternetService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * TODO-list
 * 
 * [] Validar uuid da escola (nulo, inválido, ou uuid de outra escola)
 * [] Validar uuid do provedor (nulo, inválido, ou uuid de outro provedor)
 * [] Contrato da escola A na escola B, validar
 * [] Validar adição de contrato em escola com contrato ativo
 * [] Contrato vencido, desativar automaticamente ou manualmente
 * [] Contrato cancelado, não pode ser reativado
 * [] COntrato suspenso, pode ser reativado
 * [] COntrato so pode ser adiconado se nao houver ou estiver inativo, vencido,
 * ou cancelado
 * [] Validar se a escola não tem contrato ativo
 * [] Verificar se o valor mensal é maior que 0
 * [] Verificar se a velocidade é maior ou igual a 1mbps, ex: 1mbps, 10mbps,
 * 100mbps, 1gbps
 * [] Colocar os serviços em ordem, tipo: Escola, Professor, Diretor, Aluno,
 * [] Fazer um contrato de reajuste de valor
 * [] Fazer um contrato de troca de provedor, para isso precisa de um contrato
 * ativo
 * [] Refazer o DTO de ContratoInternet para ter os campos de endereço, cidade e
 * estado
 * [] Adicionar os novos campos no DTO e Entity de ContratoInternet
 * [] testar a api
 * [] verificar o envio de email quando for criado um contrato
 * [] fazer um contrato de renovação, cancelamento e etc...
 * [] testar a api com o novo campo do contrato
 * 
 **/

@Service
@RequiredArgsConstructor
public class ContratoInternetServiceImpl implements ContratoInternetService {

	private final ContratoInternetRepository contratoInternetRepository;
	private final EscolaRepository escolaRepository;
	private final ProvedorInternetRepository provedorInternetRepository;
	private final ContratoInternetMapper contratoInternetMapper;

	@Transactional
	@Override
	public ContratoInternetDTO registrarContrato(
			UUID uuidEscola,
			UUID uuidProvedor,
			LocalDate dataContratacao,
			String velocidade,
			BigDecimal valorMensal) {
		/**
		 * fail-fast nas dependencias
		 * Separação de responsabilidade: buscar → montar → persistir → mapear
		 **/
		//idiomatico
		Escola escola = escolaRepository.findById(uuidEscola)
				.orElseThrow(() ->
						new EntityNotFoundException("Escola não encontrada para vínculo com o contrato!"));
		//idiomatico
		ProvedorInternet provedorInternet = provedorInternetRepository.findById(uuidProvedor)
				.orElseThrow(() ->
						new EntityNotFoundException("Provedor não encontrado para vínculo com o contrato!"));
		
		boolean contratoDuplicado = contratoInternetRepository
	            .existsByEscolaAndProvedorAndDataContratacao(escola, provedorInternet, dataContratacao);
		/**amo evitar I/O dentro de condicional - chamar o repository dentro do if - credo**/
		if (contratoDuplicado) {
			throw new RegraNegocioException("Já existe um contrato para esta escola, provedor e data de contratação");
		}

		ContratoInternet contrato = ContratoInternet.builder()
				.escola(escola)
				.provedor(provedorInternet)
				.dataContratacao(dataContratacao)
				.velocidade(velocidade)
				.valorMensal(valorMensal)
				.status(StatusContrato.ATIVO)
				.build();

		ContratoInternet contratoRegistrado = contratoInternetRepository.save(contrato);
		return contratoInternetMapper.entityToDto(contratoRegistrado);
	}	

	@Override
	public List<ContratoInternetDTO> buscarContratosAtivosComEscolaEProvedor() {
		return contratoInternetRepository.findContratosAtivosComEscolaEProvedor()
				.stream().map(contratoInternetMapper::entityToDto).toList();
	}

	@Override
	public List<ContratoInternetDTO> buscarPorEscolaEStatus(UUID uuidEscola, StatusContrato status) {
		return contratoInternetRepository.findByEscola_UuidAndStatus(uuidEscola, status)
				.stream().map(contratoInternetMapper::entityToDto).toList();
	}

//	Conceitual - verboso e sem ganho real
//	Optional<Escola> escolaOpt = escolaRepository.findById(uuidEscola);
//	Escola escola = escolaOpt.orElseThrow(() ->
//	        new EntityNotFoundException("Escola não encontrada para vínculo com o contrato!"));

}
