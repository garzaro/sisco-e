package com.sisco_e.escola.service;

import java.util.List;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.model.enums.TipoEscola;

@Service
public interface EscolaService {

	EscolaResponseDTO cadastrarEscola(EscolaRequestDTO escolaRequestDto);

	void validarEscola(EscolaRequestDTO escolaRequestDto);

	void deletarEscola(UUID escolaUuid);
	/**
	 * Atualiza os dados de uma escola existente identificada pelo seu UUID.
	 */
	EscolaResponseDTO atualizarEscola(UUID escolaUuid, EscolaRequestDTO escolaRequestDto);

	EscolaResponseDTO buscarEscolaPorCodigo(String codigoEscola);

	EscolaResponseDTO buscarEscolaPorId(UUID escolaUuid);

	/**
	 * Busca rápida limitada para preenchimento de autocomplete/sugestões na interface.
	 */
	List<EscolaResponseDTO> buscarByNomeEscolaContainingIgnoreCaseOrderByNomeEscolaAsc(String parteNomeEscola);

	Page<EscolaResponseDTO> buscarEscolasCadastradas(Pageable pageable);

	Page<EscolaResponseDTO> buscarEscolaPorMunicipio(String municipio, Pageable pageable);

	Page<EscolaResponseDTO> buscarEscolaPorEstado(String estado, Pageable pageable);

	Page<EscolaResponseDTO> buscarEscolaPorTipo(TipoEscola tipoEscola, Pageable pageable);
}