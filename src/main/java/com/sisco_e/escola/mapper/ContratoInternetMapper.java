package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.ContratoInternetDTO;
import com.sisco_e.escola.model.entity.ContratoInternet;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ContratoInternetMapper {

	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "provedor", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	ContratoInternet DtoToEntity(ContratoInternetDTO dto);

	@Mapping(target = "uuidEscola", source = "escola.uuid")
	@Mapping(target = "uuidProvedor", source = "provedor.uuid")
	ContratoInternetDTO entityToDto(ContratoInternet entity);
}
