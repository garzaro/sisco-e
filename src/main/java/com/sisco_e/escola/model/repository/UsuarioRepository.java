package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	 
    boolean existsByEmail(String email);
  
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByNomeCompleto(String nomeCompleto);
       
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByCpf(String cpf);    

    /**Busca por parte do nome (Query Method padrão)**/ 
    List<Usuario> findByNomeCompletoContainingIgnoreCase(String parteNome);

    /**
     * Busca personalizada usando JPQL para maior flexibilidade.
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nomeCompleto) LIKE LOWER(concat('%', :parteDoNome, '%'))")
    List<Usuario> buscarUsuarioDigitandoApenasParteDoNome(@Param("parteDoNome") String parteDoNome);

}
