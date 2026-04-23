package com.sisco.escola.model.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sisco.escola.model.entity.Usuario;

@Repository
public interface UsuarioRepository {
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
