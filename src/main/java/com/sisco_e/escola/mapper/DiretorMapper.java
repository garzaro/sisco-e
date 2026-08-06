package com.sisco_e.escola.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.sisco_e.escola.api.dto.DiretorDTO;
import com.sisco_e.escola.model.entity.Diretor;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DiretorMapper {

	@Mapping(target = "cpfDiretor", source = "cpf")
	@Mapping(target = "emailCorporativo", source = "email")
	@Mapping(target = "escola", ignore = true)
	@Mapping(target = "dataCadastro", ignore = true)
	@Mapping(target = "dataAtualizacao", ignore = true)
	Diretor DtoToEntity(DiretorDTO dto);

	@Mapping(target = "cpf", source = "cpfDiretor")
	@Mapping(target = "email", source = "emailCorporativo")
	@Mapping(target = "uuidEscola", source = "escola.uuid")
	DiretorDTO entityToDto(Diretor entity);
}
