package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.mapstruct.*;

import com.sisco_e.escola.model.entity.Escola;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EscolaMapper {

	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	@Mapping(target = "isAtivo", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "diretor", ignore = true) 
	Escola dtoToEntity(EscolaRequestDTO requestDto);

	EscolaResponseDTO entityToDto(Escola responseResponse);

	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "diretor", ignore = true)
	@Mapping(target = "isAtivo", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	void atualizarEscola(EscolaRequestDTO requestDto, @MappingTarget Escola escola);
}

/**
 * @Mapping(source = "uuid", target = "escolaUuid")
 * EscolaResponseDTO toResponse(Escola escola);
 *
 * source é o campo da entidade (uuid) e target é o do DTO (escolaUuid).
 *
 *  Se for o contrário (entidade escolaUuid, DTO uuid), é só trocar os dois.
 *
 * Se o DTO (request) de entrada não tiver o campo (o UUID é gerado pelo banco), ignore o alvo:
 * toEntity - @Mapping(target = "uuid", ignore = true)
 *
 * No update, o uuid não pode ser sobrescrito pelo DTO (se vier nulo ou diferente, corrompe a identidade da entidade).
 */

