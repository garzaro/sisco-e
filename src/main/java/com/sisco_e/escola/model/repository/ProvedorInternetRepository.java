package com.sisco_e.escola.model.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisco_e.escola.model.entity.ProvedorInternet;

@Repository
public interface ProvedorInternetRepository extends JpaRepository<ProvedorInternet, UUID> {

    boolean existsByNomeProvedor(String nomeProvedor);

    boolean existsByCnpj(String cnpj);

    boolean existsByCanalSuportePrioritario(String canalSuportePrioritario);

    Optional<ProvedorInternet> findByNomeProvedor(String nomeProvedor);

    Optional<ProvedorInternet> findByCnpj(String cnpj);

    List<ProvedorInternet> findByNomeProvedorContainingIgnoreCase(String nomeProvedor);

    @Query("""
        SELECT p
        FROM ProvedorInternet p
        WHERE LOWER(p.nomeProvedor) LIKE LOWER(CONCAT('%', :nome, '%'))
        ORDER BY p.nomeProvedor ASC
        """)
    List<ProvedorInternet> buscarPorNomeParcial(@Param("nome") String nome);

    @Query("""
        SELECT p
        FROM ProvedorInternet p
        WHERE p.cnpj = :cnpj
          AND p.canalSuportePrioritario = :canalSuportePrioritario
        """)
    Optional<ProvedorInternet> buscarPorCnpjECanalSuportePrioritario(
            @Param("cnpj") String cnpj,
            @Param("canalSuportePrioritario") String canalSuportePrioritario);
 
}
