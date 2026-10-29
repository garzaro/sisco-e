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

	List<EscolaRequestDTO> buscarTodasAsEscolasCadastradas();

	Optional<EscolaRequestDTO> buscarEscolaPorId(UUID uuid);

	Optional<EscolaRequestDTO> buscarEscolaPorNome(String nomeEscola);

	List<EscolaRequestDTO> buscarEscolaPorMunicipio(String municipio);

	List<EscolaRequestDTO> buscarEscolaPorEstado(String estado);

	List<EscolaRequestDTO> buscarEscolaPorTipo(TipoEscola tipoEscola);

	List<EscolaRequestDTO> buscarEscolaPorParteDoNome(String parteNomeEscola);

	EscolaRequestDTO atualizarEscola(EscolaRequestDTO escolaRequestDto);

	void deletarEscola(UUID uuid);

	Optional<EscolaRequestDTO> obterEscolaPorId(UUID uuid);
}