package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interface de repositório para a entidade {@link Usuario}.
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * CRUD padrão para {@code Usuario}, além de métodos de consulta especializados.
 * Os métodos desta interface são utilizados pela camada de serviço para
 * realizar operações de negócio relacionadas a usuários.
 * </p>
 * <p>
 * Utiliza a especificação do Spring Data JPA para buscar usuários por
 * e-mail, CPF e nome de usuário.
 * </p>
 * <p>
 * A anotação @Repository marca esta interface como um componente de
 * persistência de dados, permitindo que o Spring a injete automaticamente
 * em outras partes da aplicação.
 * </p>
 * <p>
 * O tipo de entidade é {@code Usuario} e o tipo do identificador é {@code Long}.
 * </p>
 * Abstracao de repository que representa a entidade Usuario
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /*existe um usuario com o email informado*/
    boolean existsByEmail(String email);
    
    /*existe um usuario com o cpf informado*/
    boolean existsByCpf(String cpf);    
    
    /*existe um usuario com o username informado*/
    boolean existsByUsuario(String usuario);
    
    /*busca um usuario por email informado*/
    Optional<Usuario> findByEmail(String email);
    
    /*busca um usuario por cpf informado*/
    Optional<Usuario> findByCpf(String cpf);

    /*busca um usuario pelo username informado*/
    Optional<Usuario> findByUsuario(String usuario);

}


