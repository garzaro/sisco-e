package com.sisco_e.escola.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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


@Service
@RequiredArgsConstructor
public class ContratoInternetServiceImpl implements ContratoInternetService {
	
	private final ContratoInternetRepository contratoInternetRepository;
	private final EscolaRepository escolaRepository;
	private final ProvedorInternetRepository provedorInternetRepository;
	private final ContratoInternetMapper contratoInternetMapper;

	@Override
	public ContratoInternetDTO contratar(UUID escolaUuid, UUID provedorUuid, LocalDate dataContratacao,
			String velocidade, BigDecimal valorMensal) {
		Escola escola = escolaRepository.findById(escolaUuid)
				.orElseThrow(() -> new EntityNotFoundException("Escola não encontrada"));

			ProvedorInternet provedor = provedorInternetRepository.findById(provedorUuid)
				.orElseThrow(() -> new EntityNotFoundException("Provedor de internet não encontrado"));

			ContratoInternet contrato = ContratoInternet.builder()
				.escola(escola)
				.provedor(provedor)
				.dataContratacao(dataContratacao)
				.velocidade(velocidade)
				.valorMensal(valorMensal)
				.status(StatusContrato.ATIVO)
				.build();

			return contratoInternetMapper.entityToDto(contratoInternetRepository.save(contrato));
		
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

}
