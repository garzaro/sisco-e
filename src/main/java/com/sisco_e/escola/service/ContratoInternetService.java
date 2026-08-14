package com.sisco_e.escola.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.ContratoInternetDTO;
import com.sisco_e.escola.model.enums.StatusContrato;

public interface ContratoInternetService {

	ContratoInternetDTO contratar(
			UUID uuidEscola,
			UUID uuidProvedor,
			LocalDate dataContratacao,
			String velocidade,
			BigDecimal valorMensal
	);

	List<ContratoInternetDTO> buscarContratosAtivosComEscolaEProvedor();

	List<ContratoInternetDTO> buscarPorEscolaEStatus(UUID uuidEscola, StatusContrato status);

}
