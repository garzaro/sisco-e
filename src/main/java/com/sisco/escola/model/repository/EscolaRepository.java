package com.sisco.escola.model.repository;

import com.sisco.escola.model.entity.Escola;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {
    
	/*verifica a existencia de uma escola pelo id*/
    boolean existsById(Long id);
    /*verifica a existencia de uma escola por nome*/
    boolean existsByNomeEscola(String escola);
    /*verifica a existencia de uma escola pelo codigo*/
    boolean existsByCodigoEscola(String codigo);
    /*procura uma escola pelo id*/
    Optional<Escola> findById(Long id);
    /*procura uma escola pelo nome*/
    Escola findByNomeEscola(String escola);
    /*procura uma escola pelo codigo*/
    Optional<Escola> findByCodigoEscola(String codigo);
    /*listar todas as escolas*/
    List<Escola> findAll();
}
