package com.sisco_e.escola.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.EscolaDTO;
import com.sisco_e.escola.model.enums.TipoEscola;

@Service
public interface EscolaService {

	EscolaDTO cadastrarEscola(EscolaDTO escolaDto);

	void validarEscola(EscolaDTO escolaDto);

	List<EscolaDTO> buscarTodasAsEscolasCadastradas();

	Optional<EscolaDTO> buscarEscolaPorId(UUID uuid);

	Optional<EscolaDTO> buscarEscolaPorNome(String nomeEscola);

	List<EscolaDTO> buscarEscolaPorMunicipio(String municipio);

	List<EscolaDTO> buscarEscolaPorEstado(String estado);

	List<EscolaDTO> buscarEscolaPorTipo(TipoEscola tipoEscola);

	List<EscolaDTO> buscarEscolaPorParteDoNome(String parteNomeEscola);

	EscolaDTO atualizarEscola(EscolaDTO escolaDto);

	void deletarEscola(UUID uuid);

	Optional<EscolaDTO> obterEscolaPorId(UUID uuid);
}