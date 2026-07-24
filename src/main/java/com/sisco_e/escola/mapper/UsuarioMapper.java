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
) //isso é perfeito para métodos de atualização (PUT/PATCH), pois evita que campos nulos enviados na requisição sobrescrevam dados válidos que já existem no banco
public interface UsuarioMapper {
    /**
     * DTO para Entidade
     * Converte um {@link UsuarioDTO} em entidade {@link Usuario}.
     *
     * Usado principalmente na operação de cadastro.
     *
     * <p><strong>Atenção:</strong> o campo {@code dataCadastro} é gerenciado pelo Hibernate
     * ({@code @CreationTimestamp}) e, portanto, é ignorado nesta conversão.
     *
     * @param dto objeto de transferência de dados
     * @return entidade pronta para persistência
     */
    @Mapping(target = "dataCadastro", ignore = true)
    Usuario DtoToEntity(UsuarioDTO dto);

    /**
     * Entidade para DTO
     * Converte uma entidade {@link Usuario} em {@link UsuarioDTO}.
     * O campo {@code senha} é omitido intencionalmente para não expor o hash.
     *
     * @param entity entidade JPA
     * @return DTO sem o campo senha
     */
    @Mapping(target = "password", ignore = true)
    UsuarioDTO entityToDto(Usuario entity);
}
