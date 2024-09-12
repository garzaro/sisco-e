package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {
    
    /*verifica a existencia de uma escola por nome*/
    boolean existsByNomeEscola(String nomeEscola);
    
    /*verifica a existencia de uma escola por codigo*/
    boolean existsByCodigoEscola(String cadastroEscola);
    
    /*procura uma escola pelo nome*/
    List<Escola> findByNomeEscola(String nomeEscola);
    
    /*procura uma escola pelo codigo*/
    Optional<Escola> findByCodigoEscola(String cadastroEscola);
}
