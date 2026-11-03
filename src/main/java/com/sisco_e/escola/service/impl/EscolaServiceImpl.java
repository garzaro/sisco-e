package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.springframework.stereotype.Service;

import com.sisco_e.escola.exception.RegraNegocioException;
import com.sisco_e.escola.mapper.EscolaMapper;
import com.sisco_e.escola.model.entity.Escola;
import com.sisco_e.escola.model.enums.TipoEscola;
import com.sisco_e.escola.model.repository.EscolaRepository;
import com.sisco_e.escola.service.EscolaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * TODO-list
 * [x] Salvar escola
 * [x] Validar escola
 * [x] Atualizar escola
 * []
 * **/

@Service
@RequiredArgsConstructor
public class EscolaServiceImpl implements EscolaService {

	private final EscolaRepository escolaRepository;
	private final EscolaMapper escolaMapper;

	@Override
	@Transactional
	public EscolaResponseDTO cadastrarEscola(EscolaRequestDTO escolaRequestDto) {
		validarEscola(escolaRequestDto);
		Escola entity = escolaMapper.DtoToEntity(escolaRequestDto);
		/** Segue o boolean do domínio: toda escola cadastrada inicia ativa. */
		entity.setIsAtivo(Boolean.TRUE);
		Escola escolaSalva = escolaRepository.save(entity);
		return escolaMapper.entityToDto(escolaSalva);
	}

	@Override
	public void validarEscola(EscolaRequestDTO escolaRequestDto) {
		if (escolaRequestDto == null) {
			throw new RegraNegocioException("Dados da escola não informados!");
		}

		boolean escolaDuplicada = escolaRepository
				.existsByCodigoEscola(escolaRequestDto.getCodigoEscola());

		if (escolaDuplicada) {
			throw new RegraNegocioException("Verifique o código da escola e tente novamente!");
		}
	}

	@Override
	@Transactional
	public EscolaResponseDTO atualizarEscola(UUID escolaUuid, EscolaRequestDTO escolaRequestDto) {
		Objects.requireNonNull(escolaUuid,"Não foi possível identificar a escola para atualização!");

		Escola escolaRetornada = escolaRepository.findById(escolaUuid)
				.orElseThrow(() -> new RegraNegocioException("Não foi possível a atualização da escola!"));
		/**o mapstruct atauliza os campos da entidade conforme o dto na moral, magico**/
		escolaMapper.atualizarEscolaFromDto(escolaRequestDto, escolaRetornada);

		Escola escolaAtualizada = escolaRepository.save(escolaRetornada);

		return escolaMapper.entityToDto(escolaAtualizada);
	}

	@Override
	public List<EscolaResponseDTO> buscarTodasAsEscolasCadastradas() {
		return escolaRepository.findAll().stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public Optional<EscolaResponseDTO> buscarEscolaPorId(UUID uuid) {
		return escolaRepository.findById(uuid).map(escolaMapper::entityToRequestDto);
	}

	@Override
	public Optional<EscolaResponseDTO> buscarEscolaPorNome(String nomeEscola) {
		return escolaRepository.findByNomeEscola(nomeEscola).map(escolaMapper::entityToRequestDto);
	}

	@Override
	public List<EscolaResponseDTO> buscarEscolaPorMunicipio(String municipio) {
		return escolaRepository.findByMunicipio(municipio).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaResponseDTO> buscarEscolaPorEstado(String estado) {
		return escolaRepository.findByEstado(estado).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaResponseDTO> buscarEscolaPorTipo(TipoEscola tipoEscola) {
		return escolaRepository.findByTipoEscola(tipoEscola).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaResponseDTO> buscarEscolaPorParteDoNome(String parteNomeEscola) {
		return escolaRepository.findByNomeEscolaContainingIgnoreCase(parteNomeEscola).stream()
			.map(escolaMapper::entityToRequestDto)
			.toList();
	}

	@Override
	@Transactional
	public void deletarEscola(UUID uuid) {
		if (!escolaRepository.existsById(uuid)) {
			throw new RegraNegocioException("Escola não encontrada para exclusão");
		}
		escolaRepository.deleteById(uuid);
	}

	@Override
	public Optional<EscolaRequestDTO> obterEscolaPorId(UUID uuid) {
		return buscarEscolaPorId(uuid);
	}
}

// boolean duplicada = escolaRepository.existsByNomeEscola(escolaRequestDto.getNomeEscola(),
// 	escolaRequestDto.getCidade(), escolaRequestDto.getEstado(), escolaRequestDto.getUuid());

// if (duplicada) {
// 	throw new RegraNegocioException("Já existe escola com esse nome na cidade e estado informados");
// }