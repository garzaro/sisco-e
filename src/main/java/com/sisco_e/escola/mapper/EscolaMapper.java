package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.EscolaRequestDTO;
import com.sisco_e.escola.api.dto.response.EscolaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.model.entity.Escola;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EscolaMapper {

	// @Mapping(target = "isAtivo", source = "isAtivo")
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	Escola DtoToEntity(EscolaRequestDTO dto);

	// @Mapping(target = "isAtivo", source = "isAtivo")
	@Mapping(target = "uuid", source = "uuid")
	EscolaResponseDTO entityToDto(Escola entity);

	EscolaRequestDTO entityToRequestDto(Escola entity);
}