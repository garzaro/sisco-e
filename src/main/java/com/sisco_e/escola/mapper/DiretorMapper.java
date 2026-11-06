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

	
	@Mapping(source = "cpf", target = "cpfDiretor")
	@Mapping(source = "email", target = "emailCorporativo")
	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	@Mapping(target = "isAtivo", ignore = true)
	Diretor dtoToEntity(DiretorRequestDTO dto);
		
	
	@Mapping(source = "cpfDiretor", target = "cpf")
	@Mapping(source = "emailCorporativo", target = "email")
	@Mapping(source = "escola.uuid", target = "escolaUuid")
	DiretorResponseDTO entityToDto(Diretor entity);
}
