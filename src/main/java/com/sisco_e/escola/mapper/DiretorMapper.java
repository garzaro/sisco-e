package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.DiretorRequestDTO;
import com.sisco_e.escola.api.dto.response.DiretorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.model.entity.Diretor;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DiretorMapper {

	@Mapping(target = "cpfDiretor", source = "cpf")
	@Mapping(target = "emailCorporativo", source = "email")
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	Diretor DtoToEntity(DiretorRequestDTO dto);

	@Mapping(target = "cpf", source = "cpfDiretor")
	@Mapping(target = "email", source = "emailCorporativo")
	@Mapping(target = "escolaUuid", source = "escola.uuid")
	@Mapping(target = "uuid", source = "uuid")
    DiretorResponseDTO entityToDto(Diretor entity);

	@Mapping(target = "cpf", source = "cpfDiretor")
	@Mapping(target = "email", source = "emailCorporativo")
	@Mapping(target = "escolaUuid", source = "escola.uuid")
	@Mapping(target = "uuid", source = "uuid")
	DiretorRequestDTO entityToRequestDto(Diretor entity);
}
