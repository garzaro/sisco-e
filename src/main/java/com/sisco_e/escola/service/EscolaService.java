package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.model.enums.TipoEscola;

@Service
public interface EscolaService {

	EscolaResponseDTO cadastrarEscola(EscolaRequestDTO escolaRequestDto);

	void validarEscola(EscolaRequestDTO escolaRequestDto);

	EscolaResponseDTO atualizarEscola(UUID escolaUuid, EscolaRequestDTO escolaRequestDto);

	List<EscolaResponseDTO> buscarTodasAsEscolasCadastradas();

	Optional<EscolaResponseDTO> buscarEscolaPorId(UUID uuid);

	Optional<EscolaResponseDTO> buscarEscolaPorNome(String nomeEscola);

	List<EscolaResponseDTO> buscarEscolaPorMunicipio(String municipio);

	List<EscolaResponseDTO> buscarEscolaPorEstado(String estado);

	List<EscolaResponseDTO> buscarEscolaPorTipo(TipoEscola tipoEscola);

	List<EscolaResponseDTO> buscarEscolaPorParteDoNome(String parteNomeEscola);

	void deletarEscola(UUID uuid);

}