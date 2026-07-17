package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de repositório para a entidade {@link Usuario}.
 * 
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * CRUD padrão para {@code Usuario}, além de métodos de consulta especializados.
 * Os métodos desta interface são utilizados pela camada de serviço para
 * realizar operações de negócio relacionadas a usuários.
 * 
 * Utiliza a especificação do Spring Data JPA para buscar usuários por
 * e-mail, CPF e nome de usuário.
 * 
 * A anotação @Repository marca esta interface como um componente de
 * persistência de dados, permitindo que o Spring a injete automaticamente
 * em outras partes da aplicação.
 * 
 * O tipo de entidade é {@code Usuario} e o tipo do identificador é {@code Long}.
 * 
 * Abstracao de repository que representa a entidade Usuario
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
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


