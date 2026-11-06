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

	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "provedor", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	ContratoInternet dtoToEntity(ContratoInternetRequestDTO dto);

	@Mapping(source = "escola.uuid", target = "uuidEscola")
	@Mapping(source = "provedor.uuid", target = "uuidProvedor")
	ContratoInternetResponseDto entityToDto(ContratoInternet entity);
}
