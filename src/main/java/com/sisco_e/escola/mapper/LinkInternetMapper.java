package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.LinkInternetDTO;
import com.sisco_e.escola.model.entity.LinkInternet;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LinkInternetMapper {

	@Mapping(target = "contratoInternet", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	LinkInternet DtoToEntity(LinkInternetDTO dto);

	@Mapping(target = "uuidContratoInternet", source = "contratoInternet.uuid")
	LinkInternetDTO entityToDto(LinkInternet entity);
}
