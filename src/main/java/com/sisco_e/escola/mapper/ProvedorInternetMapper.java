package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.request.ProvedorInternetRequestDTO;
import com.sisco_e.escola.api.dto.response.ProvedorInternetResponseDTO;
import com.sisco_e.escola.model.entity.ProvedorInternet;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProvedorInternetMapper {

	ProvedorInternet DtoToEntity(ProvedorInternetRequestDTO dto);

	ProvedorInternetResponseDTO entityToDto(ProvedorInternet entity);

	ProvedorInternetRequestDTO entityToRequestDto(ProvedorInternet entity);
}
