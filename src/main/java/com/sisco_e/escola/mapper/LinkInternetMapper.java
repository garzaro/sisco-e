package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.LinkInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.LinkInternetResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sisco_e.escola.model.entity.LinkInternet;

//,
//nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS

@Mapper(componentModel = "spring")
public interface LinkInternetMapper {

	@Mapping(target = "uuid", ignore = true)
	@Mapping(source = "ipWan", target = "ipPublico")
	@Mapping(target = "contratoInternet", ignore = true)
	@Mapping(target = "provedorInternet", ignore = true)
	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "isAtivo", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	LinkInternet dtoToEntity(LinkInternetRequestDTO dto);

	@Mapping(source = "ipPublico", target = "ipWan")
	@Mapping(source = "contratoInternet.uuid", target = "contratoInternetUuid")
	@Mapping(source = "provedorInternet.uuid", target = "provedorInternetUuid")
	@Mapping(source = "escola.uuid", target = "escolaUuid")
	LinkInternetResponseDTO entityToDto(LinkInternet entity);
}
