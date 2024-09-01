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
    
    /*busca um usuario por email*/
    Optional<Usuario> findByEmail(String email);
    
}
