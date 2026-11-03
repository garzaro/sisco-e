package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.mapstruct.*;

import com.sisco_e.escola.model.entity.Escola;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EscolaMapper {

	// @Mapping(target = "isAtivo", source = "isAtivo")
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	Escola DtoToEntity(EscolaRequestDTO requestDto);

	// @Mapping(target = "isAtivo", source = "isAtivo")
	@Mapping(target = "uuid", source = "uuid")
	EscolaResponseDTO entityToDto(Escola escolaResponse);

	void atualizarEscolaFromDto(EscolaRequestDTO requestDto, @MappingTarget Escola escola);

//	EscolaRequestDTO entityToRequestDto(Escola entity);
}