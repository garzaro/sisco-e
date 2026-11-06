package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.request.ProvedorInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ProvedorInternetResponseDTO;
import com.sisco_e.escola.model.entity.ProvedorInternet;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProvedorInternetMapper {

	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "isAtivo", ignore = true)
	ProvedorInternet dtoToEntity(ProvedorInternetRequestDTO provedorInternetRequestDTO);

	ProvedorInternetResponseDTO entityToDto(ProvedorInternet provedorInternet);
}
