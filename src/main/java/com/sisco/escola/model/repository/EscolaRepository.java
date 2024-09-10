package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {
    
    /*verifica a existencia de uma escola por nome*/
    boolean existsByNomeEscola(String nomeEscola);
    
    /*verifica a existencia de uma escola por inep*/
    boolean existsByCadastroEscola(Integer cadastroEscola);
    
    /*procura uma escola pelo nome ou inep*/
    Optional<Escola> findByNomeEscola(String nomeEscola);
    
    /*procura uma escola pelo nome ou inep*/
    Optional<Escola> findByNomeEscolaOrCadastroEscola(String nomeEscola, Integer cadastroEscola);
}
