package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EscolaRepository extends JpaRepository<Escola, Long> {
    
    /*verifica a existencia de uma escola no banco de dados*/
    boolean existsByNomeEscola(String nomeEscola);
    boolean existsByCadastroEscola(String cadastroEscola);
    
    /*procura uma escola pelo nome no banco de dados*/
    Optional<Escola> findByNomeEscolaOrCadastroEscola(String nomeEscola, String cadastroEscola);
    
    Escola findByNomeEscola(String nomeEscola);
}
