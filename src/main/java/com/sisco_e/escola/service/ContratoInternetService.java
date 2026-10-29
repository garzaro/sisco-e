package com.sisco_e.escola.service;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.ContratoInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ContratoInternetResponseDto;
import com.sisco_e.escola.model.enums.StatusContrato;

public interface ContratoInternetService {

	ContratoInternetResponseDto cadastrarContrato(ContratoInternetRequestDTO contratoDto);
	
	void validarContrato(ContratoInternetRequestDTO contratoDto);

	List<ContratoInternetRequestDTO> buscarContratosAtivosComEscolaEProvedor();

	List<ContratoInternetRequestDTO> buscarPorEscolaEStatus(UUID uuidEscola, StatusContrato status);
}
