package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    boolean existsByEmailLogin(String emailLogin);
}
