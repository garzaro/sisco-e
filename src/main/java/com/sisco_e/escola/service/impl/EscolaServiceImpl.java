package com.sisco_e.escola.service.impl;

import java.util.List;
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

		boolean escolaDuplicada = escolaRepository.existsByCodigoEscola(escolaRequestDto.getCodigoEscola());

		if (escolaDuplicada) {
			throw new RegraNegocioException("Verifique o código da escola e tente novamente!");
		}
	}

	@Override
	public List<EscolaRequestDTO> buscarTodasAsEscolasCadastradas() {
		return escolaRepository.findAll().stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public Optional<EscolaRequestDTO> buscarEscolaPorId(UUID uuid) {
		return escolaRepository.findById(uuid).map(escolaMapper::entityToRequestDto);
	}

	@Override
	public Optional<EscolaRequestDTO> buscarEscolaPorNome(String nomeEscola) {
		return escolaRepository.findByNomeEscola(nomeEscola).map(escolaMapper::entityToRequestDto);
	}

	@Override
	public List<EscolaRequestDTO> buscarEscolaPorMunicipio(String municipio) {
		return escolaRepository.findByMunicipio(municipio).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaRequestDTO> buscarEscolaPorEstado(String estado) {
		return escolaRepository.findByEstado(estado).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaRequestDTO> buscarEscolaPorTipo(TipoEscola tipoEscola) {
		return escolaRepository.findByTipoEscola(tipoEscola).stream().map(escolaMapper::entityToRequestDto).toList();
	}

	@Override
	public List<EscolaRequestDTO> buscarEscolaPorParteDoNome(String parteNomeEscola) {
		return escolaRepository.findByNomeEscolaContainingIgnoreCase(parteNomeEscola).stream()
			.map(escolaMapper::entityToRequestDto)
			.toList();
	}

	@Override
	@Transactional
	public EscolaRequestDTO atualizarEscola(EscolaRequestDTO escolaRequestDto) {
		if (escolaRequestDto == null || escolaRequestDto.getUuid() == null) {
			throw new RegraNegocioException("Id da escola é obrigatório para atualização");
		}

		Escola escolaExistente = escolaRepository.findById(escolaRequestDto.getUuid())
			.orElseThrow(() -> new RegraNegocioException("Escola não encontrada para atualização"));

		// boolean duplicada = escolaRepository.existsByNomeEscola(escolaRequestDto.getNomeEscola(),
		// 	escolaRequestDto.getCidade(), escolaRequestDto.getEstado(), escolaRequestDto.getUuid());

		// if (duplicada) {
		// 	throw new RegraNegocioException("Já existe escola com esse nome na cidade e estado informados");
		// }

		escolaExistente.setNomeEscola(escolaRequestDto.getNomeEscola());
		escolaExistente.setCodigoEscola(escolaRequestDto.getCodigoEscola());
		escolaExistente.setMunicipio(escolaRequestDto.getMunicipio());
		escolaExistente.setEstado(escolaRequestDto.getEstado());
		escolaExistente.setCep(escolaRequestDto.getCep());
		escolaExistente.setLogradouro(escolaRequestDto.getLogradouro());
		escolaExistente.setTipoEscola(escolaRequestDto.getTipoEscola());

		Escola escolaAtualizada = escolaRepository.save(escolaExistente);
		return null; // escolaMapper.entityToDto(escolaAtualizada);
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