package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /*existe um usuario com um email*/
    boolean existsByEmail(String email);
    
    /*existe um usuario com um cpf*/
    boolean existsByCpf(String cpf);
    
    /*verifica a existencia de um usuario por nome, ignora o id atual e salva, caso seja atualização*/
    boolean existsByEmailAndIdNot(String email, Long id);
    
    /*verifica a existencia de uma escola por codigo, ignora o id atual e salva, caso seja atualização*/
    boolean existsByCpfAndIdNot(String cpf, Long id);
    
    /*busca um usuario por email*/
    Optional<Usuario> findByEmail(String email);
    
    /*busca um usuario por email - IMPLEMENTAR SERVICO*/
    Optional<Usuario> findByCpf(String cpf);
    
}
