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
	// Métodos de existência para validação
    boolean existsByEmail(String email);
    
 // Métodos de existência para validação
    boolean existsByCpf(String cpf);

    // Query Methods básicos
    Optional<Usuario> findByNomeCompleto(String nomeCompleto);
       
    // Mantido por compatibilidade com UsuarioServiceImpl.findByemail
    Optional<Usuario> findByemail(String email);

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByCpf(String cpf);    

    // Método para autenticação
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    // Busca por parte do nome (Query Method padrão)
    List<Usuario> findByNomeCompletoContainingIgnoreCase(String parteNome);

    /**
     * Busca personalizada usando JPQL para maior flexibilidade.
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nomeCompleto) LIKE LOWER(concat('%', :parteDoNome, '%'))")
    List<Usuario> buscarUsuarioDigitandoApenasParteDoNome(@Param("parteDoNome") String parteDoNome);

}
