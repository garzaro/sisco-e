package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.EscolaDTO;
import com.sisco_e.escola.model.entity.Escola;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EscolaMapper {

	@Mapping(target = "isAtivo", source = "isAtivo")
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	Escola DtoToEntity(EscolaDTO dto);

	@Mapping(target = "isAtivo", source = "isAtivo")
	EscolaDTO entityToDto(Escola entity);
}