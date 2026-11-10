package com.sisco_e.escola.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import com.sisco_e.escola.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * [x] Buscar Escola Por Id
 * [x] Buscar Escola Por Codigo
 * [x] Buscar Escolas Cadastradas
 * [x] Buscar escola por nome - ver minimo de char para consulta, ex: 3, para que a cosnulta nao va ao banco co apenas uma letra
 * [x] Buscar Escola Por Municipio
 * [x] Buscar Escola Por Estado
 * [x] Buscar Escola Por Tipo
 * [x] Deletar Escola
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
		Escola entity = escolaMapper.dtoToEntity(escolaRequestDto);
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
				.orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível a atualização da escola!"));
		/**o mapstruct atauliza os campos da entidade conforme o dto na moral, magico**/
		escolaMapper.atualizarEscola(escolaRequestDto, escolaRetornada);

		Escola escolaAtualizada = escolaRepository.saveAndFlush(escolaRetornada); //evita retroanar a data de atualizacao antiga

		return escolaMapper.entityToDto(escolaAtualizada);
	}
//TESTAR DEPOIS ESSE AI
//	Pode manter o save(). Não causa problema, só é redundante: a entidade já é gerenciada
//	elo @Transactional, então o Hibernate grava no commit de qualquer jeito.
//	@Override
//	@Transactional
//	public ItemResponseDTO atualizarItem(UUID itemUuid, ItemRequestDTO itemRequestDto) {
//		Item item = itemRepository.findById(itemUuid)
//				.orElseThrow(() -> new RecursoNaoEncontradoException("Item", itemUuid));
//
//		itemMapper.atualizarItemFromDto(itemRequestDto, item);
//
//		return itemMapper.entityToDto(item);
//	}

	@Override
	public EscolaResponseDTO buscarEscolaPorCodigo(String codigoEscola) {
		return escolaRepository.findByCodigoEscola(codigoEscola)
				.map(escolaMapper::entityToDto)
				.orElseThrow(() -> new
						RecursoNaoEncontradoException("Não foi possível encontrar a escola informada!"));
	}

	@Override
	public Page<EscolaResponseDTO> buscarEscolasCadastradas(Pageable pageable) {
		return escolaRepository.findAll(pageable)
				.map(escolaMapper::entityToDto); // :: atalho para a lambda = escola -> mapper.toDTO(escola)
	}

	@Override
	public EscolaResponseDTO buscarEscolaPorId(UUID escolaUuid) {
		return escolaRepository
				.findById(escolaUuid)
				.map(escolaMapper::entityToDto)
				.orElseThrow(() -> new
						RecursoNaoEncontradoException("Não foi possível encontrar a escola!"));
	}

	/**mostra de imediato 10 resultados**/
	@Override
	public List<EscolaResponseDTO> buscarByNomeEscolaContainingIgnoreCaseOrderByNomeEscolaAsc(
			String parteNomeEscola
	) {
		return escolaRepository.
				findTop10ByNomeEscolaContainingIgnoreCase(parteNomeEscola)
				.stream()
				.map(escolaMapper::entityToDto)
				.toList();
	}

	@Override
	public Page<EscolaResponseDTO> buscarEscolaPorMunicipio(String municipio, Pageable pageable) {
		return escolaRepository.findByMunicipio(municipio, pageable)
				.map(escolaMapper::entityToDto);
	}

	@Override
	public Page<EscolaResponseDTO> buscarEscolaPorEstado(String estado, Pageable pageable) {
		return escolaRepository.findByEstado(estado, pageable)
				.map(escolaMapper::entityToDto);
	}

	@Override
	public Page<EscolaResponseDTO> buscarEscolaPorTipo(TipoEscola tipoEscola, Pageable pageable) {
		return escolaRepository.findByTipoEscola(tipoEscola, pageable)
				.map(escolaMapper::entityToDto);
	}

	@Override
	@Transactional
	public void deletarEscola(UUID escolaUuid) {
		if (!escolaRepository.existsById(escolaUuid)) {
			throw new RegraNegocioException("Escola não encontrada para exclusão");
		}
		escolaRepository.deleteById(escolaUuid);
	}
}
/**
 * Estas duas linhas fazem exatamente a mesma coisa:
 * .map(escola -> mapper.toDTO(escola))
 * .map(mapper::toDTO)
 * **/
// boolean duplicada = escolaRepository.existsByNomeEscola(escolaRequestDto.getNomeEscola(),
// 	escolaRequestDto.getCidade(), escolaRequestDto.getEstado(), escolaRequestDto.getUuid());

// if (duplicada) {
// 	throw new RegraNegocioException("Já existe escola com esse nome na cidade e estado informados");
// }


