package com.sisco.escola.api.mapper;

import com.sisco.escola.api.dto.UsuarioDTO;
import com.sisco.escola.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper MapStruct responsável pela conversão entre {@link Usuario} (entidade JPA)
 * e {@link UsuarioDTO} (objeto de transferência de dados).
 *
 * <p>Regras de mapeamento:
 * <ul>
 *   <li>{@code usuario} (campo na entidade) → {@code usuario} (mesmo nome no DTO): mapeado explicitamente
 *       para deixar a intenção clara, pois ambos os lados têm nomes idênticos.</li>
 *   <li>{@code senha} é ignorada no sentido entidade → DTO, evitando exposição do hash.</li>
 *   <li>Na atualização parcial ({@link #atualizarEntidadeComDto}), propriedades nulas do DTO
 *       não sobrescrevem os valores existentes na entidade.</li>
 * </ul>
 *
 * <p>{@code componentModel = "spring"} faz o MapStruct gerar um bean Spring
 * que pode ser injetado via {@code @Autowired} / construtor.
 */
@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {

    // -------------------------------------------------------------------------
    // Entidade → DTO
    // -------------------------------------------------------------------------

    /**
     * Converte uma entidade {@link Usuario} em {@link UsuarioDTO}.
     * O campo {@code senha} é omitido intencionalmente para não expor o hash.
     *
     * @param entity entidade JPA
     * @return DTO sem o campo senha
     */
    @Mapping(target = "password", ignore = true)
    UsuarioDTO toDto(Usuario entity);

    /**
     * Converte uma lista de entidades em lista de DTOs.
     *
     * @param entities lista de entidades JPA
     * @return lista de DTOs (sem o campo senha)
     */
    List<UsuarioDTO> toDtoList(List<Usuario> entities);

    // -------------------------------------------------------------------------
    // DTO → Entidade
    // -------------------------------------------------------------------------

    /**
     * Converte um {@link UsuarioDTO} em entidade {@link Usuario}.
     * Usado principalmente na operação de cadastro.
     *
     * <p><strong>Atenção:</strong> o campo {@code dataCadastro} é gerenciado pelo Hibernate
     * ({@code @CreationTimestamp}) e, portanto, é ignorado nesta conversão.
     *
     * @param dto objeto de transferência de dados
     * @return entidade pronta para persistência
     */
    @Mapping(target = "dataCadastro", ignore = true)
    Usuario toEntity(UsuarioDTO dto);

    // -------------------------------------------------------------------------
    // Atualização parcial (merge)
    // -------------------------------------------------------------------------

    /**
     * Atualiza os campos de uma entidade existente com os valores não-nulos do DTO.
     * Campos nulos no DTO são ignorados ({@code NullValuePropertyMappingStrategy.IGNORE}),
     * preservando os valores originais da entidade.
     *
     * <p>Os campos {@code id} e {@code dataCadastro} são sempre ignorados para
     * evitar sobrescrita acidental de chaves e timestamps imutáveis.
     *
     * @param dto    fonte dos novos dados
     * @param entity entidade JPA a ser modificada in-place
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void atualizarEntidadeComDto(UsuarioDTO dto, @MappingTarget Usuario entity);
}
