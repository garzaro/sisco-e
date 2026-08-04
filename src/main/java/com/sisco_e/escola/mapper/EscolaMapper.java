package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.EscolaDTO;
import com.sisco_e.escola.model.entity.Escola;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EscolaMapper {

	Escola DtoToEntity(EscolaDTO dto);

	EscolaDTO entityToDto(Escola entity);
}