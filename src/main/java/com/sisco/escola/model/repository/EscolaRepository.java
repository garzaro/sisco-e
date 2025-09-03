package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {

    /*verifica a existencia de uma escola por nome*/
    boolean existsByNome(String escola);
    
    /*verifica a existencia de uma escola por codigo*/
    boolean existsByCodigo(String codigo);
    
    /*verifica a existencia de uma escola por nome, ignora o id atual e salva, caso seja atualização*/
    boolean existsByNomeAndIdNot(String escola, Long id);
    
    /*verifica a existencia de uma escola por codigo, ignora o id atual e salva, caso seja atualização*/
    boolean existsByCodigoAndIdNot(String codigo, long id);
    
    /*buscar escola por nome
    Optional<Escola> findByNomeIgnoreCaseContaining(String nomeEscola);*/
    
    Optional<Escola> findByNome(Escola nome);
    
    /*buscar escola por codigo*/
    Optional<Escola> findByCodigo(String codigo);
}

/*https://chatgpt.com/c/673c787b-0418-8013-ab64-fb3e99d874ca*/
 