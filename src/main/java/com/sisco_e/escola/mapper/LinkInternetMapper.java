package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.LinkInternetRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.model.entity.LinkInternet;

//,
//nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS

@Mapper(componentModel = "spring")
public interface LinkInternetMapper {

	@Mapping(target = "contratoInternet", ignore = true)
	@Mapping(target = "provedorInternet", ignore = true)
	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "ipPublico", source = "ipWan")
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	LinkInternet DtoToEntity(LinkInternetRequestDTO dto);

	@Mapping(target = "uuidContratoInternet", source = "contratoInternet.uuid")
	@Mapping(target = "ipWan", source = "ipPublico")
    LinkInternetRequestDTO entityToDto(LinkInternet entity);
}
