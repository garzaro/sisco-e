package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sisco_e.escola.api.dto.EscolaDTO;
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
	public EscolaDTO cadastrarEscola(EscolaDTO escolaDto) {
		validarEscola(escolaDto);
		Escola entity = escolaMapper.DtoToEntity(escolaDto);
		Escola escolaSalva = escolaRepository.save(entity);
		return escolaMapper.entityToDto(escolaSalva);
	}

	@Override
	public void validarEscola(EscolaDTO escolaDto) {
		if (escolaDto == null) {
			throw new RegraNegocioException("Dados da escola não informados!");
		}

		boolean escolaDuplicada = escolaRepository.existsByCodigoEscola(escolaDto.getCodigoEscola());

		if (escolaDuplicada) {
			throw new RegraNegocioException("Verifique o código da escola e tente novamente!");
		}
	}

	@Override
	public List<EscolaDTO> buscarTodasAsEscolasCadastradas() {
		return escolaRepository.findAll().stream().map(escolaMapper::entityToDto).toList();
	}

	@Override
	public Optional<EscolaDTO> buscarEscolaPorId(UUID uuid) {
		return escolaRepository.findById(uuid).map(escolaMapper::entityToDto);
	}

	@Override
	public Optional<EscolaDTO> buscarEscolaPorNome(String nomeEscola) {
		return escolaRepository.findByNomeEscola(nomeEscola).map(escolaMapper::entityToDto);
	}

	@Override
	public List<EscolaDTO> buscarEscolaPorMunicipio(String municipio) {
		return escolaRepository.findByMunicipio(municipio).stream().map(escolaMapper::entityToDto).toList();
	}

	@Override
	public List<EscolaDTO> buscarEscolaPorEstado(String estado) {
		return escolaRepository.findByEstado(estado).stream().map(escolaMapper::entityToDto).toList();
	}

	@Override
	public List<EscolaDTO> buscarEscolaPorTipo(TipoEscola tipoEscola) {
		return escolaRepository.findByTipoEscola(tipoEscola).stream().map(escolaMapper::entityToDto).toList();
	}

	@Override
	public List<EscolaDTO> buscarEscolaPorParteDoNome(String parteNomeEscola) {
		return escolaRepository.findByNomeEscolaContainingIgnoreCase(parteNomeEscola).stream()
			.map(escolaMapper::entityToDto)
			.toList();
	}

	@Override
	@Transactional
	public EscolaDTO atualizarEscola(EscolaDTO escolaDto) {
		if (escolaDto == null || escolaDto.getUuid() == null) {
			throw new RegraNegocioException("Id da escola é obrigatório para atualização");
		}

		Escola escolaExistente = escolaRepository.findById(escolaDto.getUuid())
			.orElseThrow(() -> new RegraNegocioException("Escola não encontrada para atualização"));

		// boolean duplicada = escolaRepository.existsByNomeEscola(escolaDto.getNomeEscola(),
		// 	escolaDto.getCidade(), escolaDto.getEstado(), escolaDto.getUuid());

		// if (duplicada) {
		// 	throw new RegraNegocioException("Já existe escola com esse nome na cidade e estado informados");
		// }

		escolaExistente.setNomeEscola(escolaDto.getNomeEscola());
		escolaExistente.setCodigoEscola(escolaDto.getCodigoEscola());
		escolaExistente.setMunicipio(escolaDto.getMunicipio());
		escolaExistente.setEstado(escolaDto.getEstado());
		escolaExistente.setCep(escolaDto.getCep());
		escolaExistente.setLogradouro(escolaDto.getLogradouro());
		escolaExistente.setTipoEscola(escolaDto.getTipoEscola());

		Escola escolaAtualizada = escolaRepository.save(escolaExistente);
		return escolaMapper.entityToDto(escolaAtualizada);
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
	public Optional<EscolaDTO> obterEscolaPorId(UUID uuid) {
		return buscarEscolaPorId(uuid);
	}
}