package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.request.ContratoInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ContratoInternetResponseDto;
import com.sisco_e.escola.model.entity.ContratoInternet;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ContratoInternetMapper {

	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	ContratoInternet dtoToEntity(ContratoInternetRequestDTO dto);

	@Mapping(target = "uuidEscola", source = "escola.uuid")
	@Mapping(target = "uuidProvedor", source = "provedor.uuid")
	@Mapping(target = "uuid", source = "uuid")
    ContratoInternetResponseDto entityToDto(ContratoInternet entity);

	@Mapping(target = "uuidEscola", source = "escola.uuid")
	@Mapping(target = "uuidProvedor", source = "provedor.uuid")
    ContratoInternetRequestDTO entityToRequestDto(ContratoInternet entity);
}
