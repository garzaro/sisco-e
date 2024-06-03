package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscolaRepository extends JpaRepository<Escola, Long> {
    
    boolean existsByNomeEscola(String nomeEscola);
    boolean existsByCidadeEscola(String nomeEscola);
    boolean existsByBairroEscola(String nomeEscola);
    
}
