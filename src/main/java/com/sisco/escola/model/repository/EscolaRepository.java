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
    boolean existsByNome(String escola);
    /*verifica a existencia de uma escola por codigo*/
    boolean existsByCodigo(String escola);
    /*procura uma escola pelo nome*/
    Optional<Escola> findByNome(String escola);
    /*procura uma escola pelo escola*/
    Optional<Escola> findByCodigo(String escola);
    /*listar todas as escolas*/
    List<Escola> findAll();
}
