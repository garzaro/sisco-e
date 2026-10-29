package com.sisco_e.escola.mapper;

import com.sisco_e.escola.api.dto.request.UsuarioRequestDTO;
import com.sisco_e.escola.api.dto.response.UsuarioResponseDTO;
import com.sisco_e.escola.model.entity.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
) //isso é perfeito para métodos de atualização (PUT/PATCH), 
// pois evita que campos nulos enviados na requisição sobrescrevam dados válidos que já existem no banco
public interface UsuarioMapper {
    /**entidade na entrada**/
    @Mapping(target = "dataCadastro", ignore = true)
    Usuario DtoToEntity(UsuarioRequestDTO requestDTO);

    /**@MappingTarget - para atualizar uma entidade já carregada,
     *  em vez de criar uma nova e perder o estado do JPA
     *  **/
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void atualizar(UsuarioRequestDTO request, @MappingTarget Usuario usuario);

    /**to response**/
    UsuarioResponseDTO entityToDto(Usuario usuario);

}
