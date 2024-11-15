package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {
    /*verifica a existencia de uma escola por nome*/
    boolean existsByNome(String escola);
    /*verifica a existencia de uma escola por codigo*/
    boolean existsByCodigo(String escola);
    /*buscar escola por nome
    Optional<Escola> findByNomeIgnoreCaseContaining(String nomeEscola);*/
    Optional<Escola> findByNome(Escola nome);
    /*buscar escola por codigo*/
    Optional<Escola> findByCodigo(String codigo);
}


 