package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.UsuarioDTO;
import com.sisco_e.escola.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
) //isso é perfeito para métodos de atualização (PUT/PATCH), 
// pois evita que campos nulos enviados na requisição sobrescrevam dados válidos que já existem no banco
public interface UsuarioMapper {
    
    @Mapping(target = "dataCadastro", ignore = true)
    Usuario DtoToEntity(UsuarioDTO dto);
   
    @Mapping(target = "password", ignore = true)
    UsuarioDTO entityToDto(Usuario entity);
}
